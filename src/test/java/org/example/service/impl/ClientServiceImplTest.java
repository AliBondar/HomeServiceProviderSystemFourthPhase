package org.example.service.impl;

import org.example.command.ClientSignUpCommand;
import org.example.exception.DuplicatedEmailException;
import org.example.exception.EmptyFieldException;
import org.example.exception.InvalidEmailException;
import org.example.exception.InvalidPasswordException;
import org.example.security.PasswordHash;
import org.example.service.ClientService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceImplTest {

    @Autowired
    private ClientService clientService;

    @Test
    @Order(8)
    void findClientByEmail() {
        assertEquals("ali@gmail.com",
                clientService.findClientByEmail("ali@gmail.com").get().getEmail());
    }

    @Order(6)
    @Test
    void findClientByEmailAndPassword() {
        assertEquals("ali@gmail.com",
                clientService.findClientByEmailAndPassword("ali@gmail.com", "@Ali1234").get().getEmail());
    }

    @Order(7)
    @Test
    void findClientByEmailAndPasswordWhenExceptionThrown_thenAssertionSucceeds() {
        assertEquals(Optional.empty(),
                clientService.findClientByEmailAndPassword("alibon@gmail.com", "@Ali1234"));
    }

    @Order(4)
    @Test
    void clientSignUp() {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName("ali");
        clientSignUpCommand.setLastName("bon");
        clientSignUpCommand.setEmail("ali@gmail.com");
        clientSignUpCommand.setPassword("@Ali1234");
        clientService.clientSignUp(clientSignUpCommand);
        assertEquals(clientService.findClientByEmail(clientSignUpCommand.getEmail()).get().getEmail(),
                clientSignUpCommand.getEmail());
    }

    @Order(1)
    @Test
    void ClientSignUpWhenEmptyFiledExceptionThrown_thenAssertionSucceeds() {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName("ali");
        clientSignUpCommand.setEmail("ali@gmail.com");
        clientSignUpCommand.setPassword("@Ali1234");
        assertThrows(EmptyFieldException.class, () -> {
            clientService.clientSignUp(clientSignUpCommand);
        });
    }

    @Order(2)
    @Test
    void ClientSignUpWhenInvalidEmailExceptionThrown_thenAssertionSucceeds() {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName("ali");
        clientSignUpCommand.setLastName("bon");
        clientSignUpCommand.setEmail("alicom");
        clientSignUpCommand.setPassword("@Ali1234");
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            clientService.clientSignUp(clientSignUpCommand);
        });
    }

    @Order(5)
    @Test
    void ClientSignUpWhenDuplicatedEmailExceptionThrown_thenAssertionSucceeds() {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName("ali");
        clientSignUpCommand.setLastName("bon");
        clientSignUpCommand.setEmail("ali@gmail.com");
        clientSignUpCommand.setPassword("@Ali1234");
        assertThrows(DuplicatedEmailException.class, () -> {
            clientService.clientSignUp(clientSignUpCommand);
        });
    }

    @Order(3)
    @Test
    void ClientSignUpWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
        ClientSignUpCommand clientSignUpCommand = new ClientSignUpCommand();
        clientSignUpCommand.setFirstName("ali");
        clientSignUpCommand.setLastName("bon");
        clientSignUpCommand.setEmail("ali@gmail.com");
        clientSignUpCommand.setPassword("li");
        assertThrows(InvalidPasswordException.class, () -> {
            clientService.clientSignUp(clientSignUpCommand);
        });
    }

    @Test
    void clientLogin() {
    }

    @Test
    @Order(10)
    void isClientEmailDuplicated() {
        assertEquals(true, clientService.isClientEmailDuplicated("ali@gmail.com"));
    }

    @Test
    @Order(9)
    void editClientPassword() throws NoSuchAlgorithmException {
        PasswordHash passwordHash = new PasswordHash();
        String password = "#Ali1234";
        String hashedPassword = passwordHash.createHashedPassword(password);
        clientService.editClientPassword(
                clientService.findClientByEmail("ali@gmail.com").get().getId(), password
        );
        assertEquals(hashedPassword, clientService.findClientByEmail("ali@gmail.com").get().getPassword());

    }
}