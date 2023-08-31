package org.example.service.impl;

import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
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
import org.example.service.ExpertService;
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
    @Autowired
    private ExpertService expertService;

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
    @Order(18)
    void addExpertToSubService() {
        System.out.println(expertService.findExpertByEmail("expert@gmail.com").get().getId());
        adminService.addExpertToSubService(
                expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                subServiceService.findSubServiceByDescription("testSubService").get().getId()
        );
        assertTrue(expertService.findExpertByEmail("expert@gmail.com").get().getSubServiceList().
                contains(subServiceService.findSubServiceByDescription("testSubService").get()));
    }

    @Test
    @Order(15)
    void addExpertToSubServiceWhenNotFoundTheUserExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheUserException.class, () -> {
            adminService.addExpertToSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId() + 1,
                    subServiceService.findSubServiceByDescription("testSubService").get().getId()
            );
        });
    }

    @Test
    @Order(16)
    void addExpertToSubServiceWhenNotFoundTheSubServiceExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheSubServiceException.class, () -> {
            adminService.addExpertToSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                    subServiceService.findSubServiceByDescription("testSubService").get().getId() + 1
            );
        });
    }

    @Test
    @Order(17)
    void addExpertToSubServiceWhenNotInServiceExceptionThrown_thenAssertionSucceed() {
        adminService.addService(new ServiceDTO("secondTestService"));
        Expert expert = new Expert();
        expert.setService(serviceService.findServiceByName("secondTestService").get());
        expert.setEmail("secondexpert@gmail.com");
        expert.setUserStatus(UserStatus.CONFIRMED);
        expertRepository.save(expert);
        assertThrows(NotInServiceException.class, () -> {
            adminService.addExpertToSubService(
                    expertService.findExpertByEmail("secondexpert@gmail.com").get().getId(),
                    subServiceService.findSubServiceByDescription("testSubService").get().getId()
            );
        });
    }

    @Test
    @Order(19)
    void addExpertToSubServiceWhenDuplicatedSubServiceExceptionThrown_thenAssertionSucceed() {
        assertThrows(DuplicatedSubServiceException.class, () -> {
            adminService.addExpertToSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                    subServiceService.findSubServiceByDescription("testSubService").get().getId()
            );
        });
    }

    @Test
    @Order(13)
    void addExpertToSubServiceWhenUserConfirmationExceptionThrown_thenAssertionSucceed() {
        assertThrows(UserConfirmationException.class, () -> {
            adminService.addExpertToSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                    subServiceService.findSubServiceByDescription("testSubService").get().getId()
            );
        });
    }


    @Test
    @Order(22)
    void removeExpertFromSubService() {
        adminService.removeExpertFromSubService(
                expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                subServiceService.findSubServiceByDescription("testSubService").get().getId()
                );
        assertEquals(0, expertService.findExpertByEmail("expert@gmail.com").get().getSubServiceList().size());
    }

    @Test
    @Order(20)
    void removeExpertFromSubServiceWhenUserNotFoundExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheUserException.class, () -> {
            adminService.removeExpertFromSubService(0L, 0L);
        });
    }

    @Test
    @Order(21)
    void removeExpertFromSubServiceWhenSubServiceNotFoundExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheSubServiceException.class, () -> {
            adminService.removeExpertFromSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                    0L
            );
        });
    }

    @Test
    @Order(23)
    void removeExpertFromNotInSubServiceNotFoundExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotInSubServiceException.class, () -> {
            adminService.removeExpertFromSubService(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(),
                    subServiceService.findSubServiceByDescription("testSubService").get().getId()
            );
        });
    }

    @Test
    @Order(14)
    void editExpertStatus() {
        adminService.editExpertStatus(
                expertRepository.findExpertByEmail("expert@gmail.com").get().getId(),
                UserStatus.CONFIRMED);
        assertEquals(UserStatus.CONFIRMED,
                expertRepository.findExpertByEmail("expert@gmail.com").get().getUserStatus());
    }

    @Test
    @Order(12)
    void editExpertStatusWhenNotFoundTheUserExceptionThrown_thenAssertionSucceed() {
        Expert expert = new Expert();
        expert.setFirstName("expert");
        expert.setLastName("expertian");
        expert.setEmail("expert@gmail.com");
        expert.setPassword("@Expert1234");
        expert.setUserStatus(UserStatus.NEW);
        expert.setService(serviceService.findServiceByName("testService").get());
        expertRepository.save(expert);
        assertThrows(NotFoundTheUserException.class, () -> {
            adminService.editExpertStatus(
                    expertRepository.findExpertByEmail("expert@gmail.com").get().getId() + 1,
                    UserStatus.CONFIRMED
            );
        });
    }

    @Test
    @Order(11)
    void editSubService() {
        long oldSubServiceId = subServiceService.findSubServiceByDescription("testSubService").get().getId();
        adminService.editSubService(subServiceService.findSubServiceByDescription("testSubService").get().getId(),
                15000,
                "testSubService");
        assertEquals(15000, subServiceRepository.findById(oldSubServiceId).get().getBasePrice());
    }

    @Test
    @Order(10)
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
        adminService.addService(new ServiceDTO("testService"));
        assertEquals("testService", serviceService.findServiceByName("testService").get().getName());
    }

    @Test
    @Order(4)
    void addServiceWhenDuplicatedServiceExceptionThrown_thenAssertionSucceeds() {
        assertThrows(DuplicatedServiceException.class, () -> {
            adminService.addService(new ServiceDTO("testService"));
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
        adminService.addSubService(new SubServiceDTO(
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
            adminService.addSubService(new SubServiceDTO(
                    10000, "testSubService", service
            ));
        });
    }

    @Test
    @Order(8)
    void addSubServiceWhenDuplicatedSubServiceExceptionThrown_thenAssertionSucceeds() {
        assertThrows(DuplicatedSubServiceException.class, () -> {
            adminService.addSubService(new SubServiceDTO(
                    10000, "testSubService", serviceService.findServiceByName("testService").get()
            ));
        });
    }


    @Test
    @Order(9)
    void isSubServiceDuplicated() {
        assertTrue(adminService.isSubServiceDuplicated("testSubService"
                , serviceService.findServiceByName("testService").get()));
    }

    @Test
    void showExpertsByUserStatus(){
        System.out.println(expertService.findExpertsByUserStatus(UserStatus.CONFIRMED));
    }

    @Test
    void findExpertByEmail(){
        System.out.println(expertService.findExpertByEmail("expert@gmail.com").get());
    }

}