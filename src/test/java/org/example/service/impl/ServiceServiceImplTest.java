package org.example.service.impl;

import org.example.command.ServiceCommand;
import org.example.entity.Service;
import org.example.service.AdminService;
import org.example.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceServiceImplTest {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private AdminService adminService;

    @Test
    void findServiceByName() {
        adminService.addService(new ServiceCommand("testServiceForService"));
        assertNotNull(serviceService.findServiceByName("testServiceForService"));
    }

}