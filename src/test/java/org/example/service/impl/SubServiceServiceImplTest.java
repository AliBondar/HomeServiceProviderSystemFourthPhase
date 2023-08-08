package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.repository.SubServiceRepository;
import org.example.service.AdminService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class SubServiceServiceImplTest {

    private final SubServiceService subServiceService;
    private final SubServiceRepository subServiceRepository;
    private final AdminService adminService;
    private final ServiceService serviceService;

    @Test
    void findWithServiceId() {
        assertEquals(2, subServiceRepository.findWithServiceId(2L).size());
    }

    @Test
    void addSubService() {
    }

    @Test
    void isSubServiceDuplicated() {
    }

    @Test
    void findByDescriptionAndService() {
    }

    @Test
    void findByExpertId() {
    }

    @Test
    void findByServiceId() {
        serviceService.addService(new ServiceCommand("installation"));
subServiceService.addSubService(new SubServiceCommand(10, "electrics",
        serviceService.findServiceByName("installation").get()));
        assertEquals(1, subServiceService.findByServiceId(serviceService.findServiceByName("installation").get().getId()));
    }
}