package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.entity.SubService;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.repository.ExpertRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.AdminService;
import org.example.service.ExpertService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubServiceServiceImplTest {

    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private ExpertService expertService;

    @Test
    @Order(3)
    void findByDescriptionAndService() {
        assertEquals(
                27000,
                subServiceService.findByDescriptionAndService(
                        "subServiceForSubServiceTest",
                        serviceService.findServiceByName("serviceForSubServiceTest").get()
                ).get().getBasePrice()
        );
    }

    @Test
    @Order(4)
    void findByExpertId() {
        Expert expert = new Expert();
        expert.setEmail("expertTest@gmail.com");
        expert.setFirstName("expert");
        expert.setLastName("experti");
        expert.setService(serviceService.findServiceByName("serviceForSubServiceTest").get());
        expert.setUserStatus(UserStatus.CONFIRMED);
        expertRepository.save(expert);
        adminService.addExpertToSubService(
                expert.getId(),
                subServiceService.findSubServiceByDescription("subServiceForSubServiceTest").get().getId()
        );
        assertEquals(
                27000,
                subServiceService.
                        findByExpertId(
                                expertService.findExpertByEmail("expertTest@gmail.com").get().getId()
                        )
                        .get(0).getBasePrice()
        );
    }

    @Test
    @Order(1)
    void findSubServicesByServiceId() {
        adminService.addService(new ServiceCommand("serviceForSubServiceTest"));
        adminService.addSubService(
                new SubServiceCommand(27000, "subServiceForSubServiceTest",
                        serviceService.findServiceByName("serviceForSubServiceTest").get())
        );
        assertEquals(
                27000,
                subServiceService.findSubServicesByServiceId(
                        serviceService.findServiceByName("serviceForSubServiceTest").get().getId()
                ).get(0).getBasePrice()
        );
    }

    @Test
    @Order(2)
    void findSubServiceByDescription() {
        SubService subService = new SubService();
        subService.setDescription("testSubService");
        subService.setBasePrice(34000);
        subServiceRepository.save(subService);
        assertEquals(
                34000,
                subServiceService.findSubServiceByDescription("testSubService").get().getBasePrice()
        );
    }
}