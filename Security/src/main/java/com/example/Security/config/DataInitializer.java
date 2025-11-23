package com.example.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Security.model.Role;
import com.example.Security.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            System.out.println("Seeding roles...");

            roleRepository.save(new Role("createAction"));
            roleRepository.save(new Role("updateAction"));
            roleRepository.save(new Role("deleteAction"));
            roleRepository.save(new Role("createMetric"));
            roleRepository.save(new Role("updateMetric"));
            roleRepository.save(new Role("deleteMetric"));
            roleRepository.save(new Role("triggerScan"));
            roleRepository.save(new Role("triggerProcess"));
            roleRepository.save(new Role("triggerEvaluation"));
            roleRepository.save(new Role("read")); // default permission
            roleRepository.save(new Role("admin"));
            System.out.println("Roles inserted successfully!");
        }
    }
}
