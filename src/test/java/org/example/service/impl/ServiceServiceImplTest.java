package org.example.service.impl;

import org.example.command.ServiceCommand;
import org.example.entity.Service;
import org.example.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceServiceImplTest {

    @Autowired
    private ServiceService serviceService;


    private ServiceCommand[] services = new ServiceCommand[10];

    @Test
    void findServiceByName() {

    }

    @Test
    void addService() {
        ServiceCommand serviceCommand = new ServiceCommand();
        serviceCommand.setName("serviceName");
        services[0] = serviceCommand;
        serviceService.addService(services[0]);
        assertNotNull(services[0].getId());
    }

    @Test
    void isServiceDuplicated() {
    }
}