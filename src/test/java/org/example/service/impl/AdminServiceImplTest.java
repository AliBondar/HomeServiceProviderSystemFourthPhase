package org.example.service.impl;

import org.example.entity.users.Admin;
import org.example.entity.users.enums.UserStatus;
import org.example.repository.AdminRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.SubServiceRepository;
import org.example.security.PasswordHash;
import org.example.service.AdminService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    PasswordHash passwordHash = new PasswordHash();


    @Test
    void findAdminByEmail() {
        Admin admin = new Admin();
        admin.setFirstName("ali");
        admin.setLastName("bondar");
        admin.setEmail("ali@gmail.com");
        admin.setPassword("#Ali1234");
        admin.setUserStatus(UserStatus.ADMIN);

    }



    @Test
    void findAdminByEmailAndPassword() {
        try {
            assertNotNull(adminService.findAdminByEmailAndPassword("ali@gmail.com", passwordHash.createHashedPassword("@Ali1234")));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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