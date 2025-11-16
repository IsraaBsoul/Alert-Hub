package com.example.Loader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.Loader.model.Provider;

@Service
public class SchedulerService {

    @Autowired
	private LoaderService loaderService;

    //runs every hour
    @Scheduled(cron = "0 0 * * * *") // every hour at minute 0
    public void runHourlyLoader() {

		List<Provider> providers = List.of(Provider.gitHub, Provider.jira, Provider.clickUp);
        for (Provider provider : providers) {
            loaderService.loadLatestData(provider);
        }
    }
}
