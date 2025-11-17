package com.example.Loader.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Loader.dto.TaskDto;
import com.example.Loader.model.PlatformInformation;
import com.example.Loader.model.Provider;
import com.example.Loader.repository.PlatformInformationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class LoaderService {

    @Autowired
    private PlatformInformationRepository platformInfoRepo;

    //GitHub API endpoint where the repository’s files are listed
    private final String baseUrl = "https://api.github.com/repos/teamMST/MST_AlertHub/contents/";
    //Creates an instance of HttpClient to send HTTP requests
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Transactional
    public String loadLatestData(Provider provider) {
        try {
           
            String listUrl = baseUrl + provider;
            //Sends an HTTP GET request to GitHub
            HttpRequest listRequest = HttpRequest.newBuilder()
                    .uri(URI.create(listUrl))
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();
            
            //Gets a response (list of files) as JSON text
            HttpResponse<String> listResponse = httpClient.send(listRequest, HttpResponse.BodyHandlers.ofString());

            if (listResponse.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch file list from GitHub: " + listResponse.statusCode());
            }

            //Parse JSON and find the latest file by name
            //object mapper : convert between JSON text and Java objects
            ObjectMapper mapper = new ObjectMapper();
            JsonNode filesArray = mapper.readTree(listResponse.body());

            JsonNode latestFileNode = null;
            for (JsonNode fileNode : filesArray) {
                if (latestFileNode == null ||
                        fileNode.get("name").asText().compareTo(latestFileNode.get("name").asText()) > 0) {
                    latestFileNode = fileNode;
                }
            }

            if (latestFileNode == null) {
                throw new RuntimeException("No files found for provider: " + provider);
            }

            
            String latestFileName = latestFileNode.get("name").asText();
            
            //Check if the latest file is already in the DB
            boolean exists = platformInfoRepo.existsByProviderAndFileName(provider, latestFileName);
            if (exists) {
                return "No new file for provider " + provider + ". Data is up-to-date.";
            }
            

            String downloadUrl = latestFileNode.get("download_url").asText();

         
            HttpRequest fileRequest = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .GET()
                    .build();

            HttpResponse<String> fileResponse = httpClient.send(fileRequest, HttpResponse.BodyHandlers.ofString());

            if (fileResponse.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch file content from GitHub: " + fileResponse.statusCode());
            }

            
            List<PlatformInformation> dataList = parseContent(fileResponse.body(), provider);
            //Set the file name for each row
            dataList.forEach(info -> info.setFileName(latestFileName));
            //Delete old rows for this provider and save new ones
            platformInfoRepo.deleteByProvider(provider);
            platformInfoRepo.saveAll(dataList);

            return "Data loaded successfully from " + downloadUrl;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load data for " + provider + ": " + e.getMessage(), e);
        }
    }

    public List<PlatformInformation> parseContent(String content, Provider provider) {
        List<PlatformInformation> dataList = new ArrayList<>();
        String[] lines = content.split("\n");
        boolean isHeader = true;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            if (isHeader) { isHeader = false; continue; }

            // Split CSV fields
            String[] parts = line.split(",");  

            PlatformInformation info = new PlatformInformation();
            info.setOwner_id(getValue(parts, 1));
            info.setProject(getValue(parts, 2));
            info.setTag(getValue(parts, 3));
            info.setLabel(getValue(parts, 4));
            info.setDeveloper_id(getValue(parts, 5));
            info.setTask_number(getValue(parts, 6));
            info.setEnvironment(getValue(parts, 7));
            info.setUser_story(getValue(parts, 8));
            info.setTask_point(getValue(parts, 9));
            info.setSprint(getValue(parts, 10));
            info.setTimestamp(LocalDateTime.now());
            info.setProvider(provider);

            dataList.add(info);
        }

        return dataList;
    }

    private String getValue(String[] parts, int index) {
        if (index >= parts.length || parts[index] == null || parts[index].isBlank()) return "0";
        return parts[index].trim();
    }
    
    public TaskDto toDto(PlatformInformation info) {
        TaskDto dto = new TaskDto();
        dto.setOwnerId(info.getOwner_id());
        dto.setProject(info.getProject());
        dto.setTag(info.getTag());
        dto.setLabel(info.getLabel());
        dto.setDeveloperId(info.getDeveloper_id());
        dto.setTaskNumber(info.getTask_number());
        dto.setEnvironment(info.getEnvironment());
        dto.setUserStory(info.getUser_story());
        dto.setTaskPoint(info.getTask_point());
        dto.setSprint(info.getSprint());
        dto.setTimestamp(info.getTimestamp());
        return dto;
    }

    public List<TaskDto> toDtoList(List<PlatformInformation> infos) {
        return infos.stream().map(this::toDto).toList();
    }
    
    public List<TaskDto> getAllTasks() {
        List<PlatformInformation> tasks = platformInfoRepo.findAll();
        return toDtoList(tasks);
    }

}

