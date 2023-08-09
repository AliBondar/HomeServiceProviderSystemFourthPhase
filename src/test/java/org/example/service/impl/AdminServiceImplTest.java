package org.example.service.impl;

import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.entity.Service;
import org.example.entity.users.Admin;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.AdminRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.SubServiceRepository;
import org.example.security.PasswordHash;
import org.example.service.AdminService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private ExpertRepository expertRepository;

    PasswordHash passwordHash = new PasswordHash();


    @Test
    @Order(1)
    void findAdminByEmail() throws NoSuchAlgorithmException {
        Admin admin = new Admin();
        admin.setFirstName("admin");
        admin.setLastName("adminzade");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordHash.createHashedPassword("@Admin1234"));
        admin.setUserStatus(UserStatus.ADMIN);
        admin.setSignUpDate(LocalDate.now());
        adminRepository.save(admin);
        assertEquals("admin",
                adminService.findAdminByEmail("admin@gmail.com").get().getFirstName());
    }


    @Test
    @Order(2)
    void findAdminByEmailAndPassword() throws NoSuchAlgorithmException {
        assertNotNull("admin",
                adminService.findAdminByEmailAndPassword("admin@gmail.com",
                        "@Admin1234").get().getFirstName());
    }

    @Test
    void addExpertToSubService() {

    }

    @Test
    void removeExpertFromSubService() {
    }

    @Test
    @Order(12)
    void editExpertStatus() {
        adminService.editExpertStatus(
                expertRepository.findExpertByEmail("expert@gmail.com").get().getId(),
                UserStatus.CONFIRMED);
        assertEquals(UserStatus.CONFIRMED,
                expertRepository.findExpertByEmail("expert@gmail.com").get().getUserStatus());
    }

    @Test
    @Order(11)
    void editExpertStatusWhenNotFoundTheUserExceptionThrown_thenAssertionSucceed() {
        Expert expert = new Expert();
        expert.setFirstName("expert");
        expert.setLastName("expertian");
        expert.setEmail("expert@gmail.com");
        expert.setPassword("@Expert1234");
        expert.setUserStatus(UserStatus.NEW);
        expertRepository.save(expert);
        assertThrows(NotFoundTheUserException.class, () -> {
           adminService.editExpertStatus(
                   expertRepository.findExpertByEmail("expert@gmail.com").get().getId() + 1,
                   UserStatus.CONFIRMED
           );
        });
    }

    @Test
    @Order(10)
    void editSubService() {
        long oldSubServiceId = subServiceService.findSubServiceByDescription("testSubService").get().getId();
        adminService.editSubService(subServiceService.findSubServiceByDescription("testSubService").get().getId(),
                15000,
                "testSubService");
        assertEquals(15000, subServiceRepository.findById(oldSubServiceId).get().getBasePrice());
    }

    @Test
    @Order(9)
    void editSubServiceWhenNotFoundSubServiceExceptionThrown_thenAssertionSucceeds() {
        assertThrows(NotFoundTheSubServiceException.class, () -> {
            adminService.editSubService(
                    subServiceService.findSubServiceByDescription("testSubService").get().getId() + 1,
                    15000,
                    "testSubService");
        });
    }

    @Test
    @Order(3)
    void addService() {
        adminService.addService(new ServiceCommand("testService"));
        assertEquals("testService", serviceService.findServiceByName("testService").get().getName());
    }

    @Test
    @Order(4)
    void addServiceWhenDuplicatedServiceExceptionThrown_thenAssertionSucceeds() {
        assertThrows(DuplicatedServiceException.class, () -> {
            adminService.addService(new ServiceCommand("testService"));
        });
    }

    @Test
    @Order(5)
    void isServiceDuplicated() {
        assertTrue(adminService.isServiceDuplicated("testService"));
    }

    @Test
    @Order(7)
    void addSubService() {
        adminService.addSubService(new SubServiceCommand(
                10000, "testSubService", serviceService.findServiceByName("testService").get())
        );
        assertEquals("testSubService",
                subServiceService.findSubServiceByDescription("testSubService").get().getDescription()
        );
    }

    @Test
    @Order(6)
    void addSubServiceWhenNotFoundServiceExceptionThrown_thenAssertionSucceeds() {
        Service service = new Service();
        service.setName("notFountService");
        assertThrows(NotFoundTheServiceException.class, () -> {
            adminService.addSubService(new SubServiceCommand(
                    10000, "testSubService", service
            ));
        });
    }

    @Test
    @Order(8)
    void addSubServiceWhenDuplicatedSubServiceExceptionThrown_thenAssertionSucceeds() {
        assertThrows(DuplicatedSubServiceException.class, () -> {
            adminService.addSubService(new SubServiceCommand(
                    10000, "testSubService", serviceService.findServiceByName("testService").get()
            ));
        });
    }


    @Test
    @Order(8)
    void isSubServiceDuplicated() {
        assertTrue(adminService.isSubServiceDuplicated("testSubService"
                , serviceService.findServiceByName("testService").get()));
    }


}