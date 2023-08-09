package org.example.service.impl;

import org.example.entity.users.Admin;
import org.example.entity.users.enums.UserStatus;
import org.example.repository.AdminRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.SubServiceRepository;
import org.example.security.PasswordHash;
import org.example.service.AdminService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

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
        admin.setUserStatus(UserStatus.ADMIN);
        adminRepository.save(admin);
        assertEquals("admin@gmail.com",
                adminService.findAdminByEmail("admin@gmail.com").get().getEmail());
    }


    @Test
    @Order(2)
    void findAdminByEmailAndPassword() throws NoSuchAlgorithmException {
        assertNotNull("admin@gmail.com",
                adminService.findAdminByEmailAndPassword("admin@gmail.com",
                        "@Admin1234").get().getEmail());
    }

    @Test
    void addExpertToSubService() {

    }

    @Test
    void testAddExpertToSubService() {
    }

    @Test
    void removeExpertFromSubService() {
    }

    @Test
    void editExpertStatus() {
    }

    @Test
    void editSubService() {
    }
}