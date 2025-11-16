package com.example.Loader;

import com.example.Loader.model.PlatformInformation;
import com.example.Loader.model.Provider;
import com.example.Loader.repository.PlatformInformationRepository;
import com.example.Loader.service.LoaderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.net.http.HttpClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LoaderServiceTest {

    @Mock
    private PlatformInformationRepository platformInfoRepo;

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private LoaderService loaderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //Test parseContent independently 
    
    @Test
    void testParseContent_CsvParsedCorrectly() {
        String csv = "owner_id,project,tag,label,developer_id,task_number,environment,user_story,task_point,sprint\n" +
                     "1,MyProject,v1,feature,123,10,dev,story1,5,sprint1";

        List<PlatformInformation> list = loaderService.parseContent(csv, Provider.jira);

        assertEquals(1, list.size());
        PlatformInformation info = list.get(0);

        assertEquals("1", info.getOwner_id());
        assertEquals("MyProject", info.getProject());
        assertEquals("v1", info.getTag());
        assertEquals("feature", info.getLabel());
        assertEquals("123", info.getDeveloper_id());
        assertEquals("10", info.getTask_number());
        assertEquals("dev", info.getEnvironment());
        assertEquals("story1", info.getUser_story());
        assertEquals("5", info.getTask_point());
        assertEquals("sprint1", info.getSprint());
        assertNotNull(info.getTimestamp());
    }

//    // Test loadLatestData with mocked GitHub response
//    @Test
//    void testLoadLatestData_Success() throws Exception {
//        String provider = "jira";
//
//        // Mock JSON response from GitHub list API
//        String listJson = "[{\"name\":\"file1.csv\",\"download_url\":\"http://example.com/file1.csv\"}," +
//                          "{\"name\":\"file2.csv\",\"download_url\":\"http://example.com/file2.csv\"}]";
//
//        // Mock JSON response for file content
//        String csvContent = "owner_id,project,tag,label,developer_id,task_number,environment,user_story,task_point,sprint\n" +
//                            "1,MyProject,v1,feature,123,10,dev,story1,5,sprint1";
//
//        // Mock HttpResponse for file list
//        HttpResponse<String> listResponse = mock(HttpResponse.class);
//        when(listResponse.statusCode()).thenReturn(200);
//        when(listResponse.body()).thenReturn(listJson);
//
//        // Mock HttpResponse for file content
//        HttpResponse<String> fileResponse = mock(HttpResponse.class);
//        when(fileResponse.statusCode()).thenReturn(200);
//        when(fileResponse.body()).thenReturn(csvContent);
//
//        // Mock HttpClient behavior
//        when(httpClient.send(
//                argThat(req -> req != null && req.uri().toString().endsWith(provider)),
//                any(HttpResponse.BodyHandler.class)))
//            .thenReturn(listResponse);
//
//        when(httpClient.send(
//                argThat(req -> req != null && req.uri().toString().equals("http://example.com/file2.csv")),
//                any(HttpResponse.BodyHandler.class)))
//            .thenReturn(fileResponse);
//
//        // Mock repository saveAll to do nothing
//        when(platformInfoRepo.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Call the method
//        String result = loaderService.loadLatestData(provider);
//        System.out.println("loadLatestData result = " + result);
//        // Verify repository was called
//        verify(platformInfoRepo, times(1)).saveAll(anyList());
//
//        // Assert the result string contains the latest file download URL
//       
//        System.out.println("loadLatestData result = " + result);
//        assertTrue(result.contains("http://example.com/file2.csv"));
//       
//    }
    
    @Test
    void testLoadLatestData_Success() throws Exception {
        

        // Call the method (it will actually fetch from GitHub)
        String result = loaderService.loadLatestData(Provider.jira);
        System.out.println("loadLatestData result = " + result);

        // Check that it contains the GitHub URL (actual latest file)
        assertTrue(result.contains("https://raw.githubusercontent.com/teamMST/MST_AlertHub/main/jira/"));
    }

}