package org.example.service.impl;

import org.example.command.ClientSignUpCommand;
import org.example.exception.*;
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

    @Test
    @Order(13)
    void findClientByEmailWhenClientNotFound(){
        assertEquals(Optional.empty(), clientService.findClientByEmail("bondar@gmail.com"));
    }

    @Order(6)
    @Test
    void findClientByEmailAndPassword() {
        assertEquals("ali@gmail.com",
                clientService.findClientByEmailAndPassword("ali@gmail.com", "@Ali1234").get().getEmail());
    }

    @Test
    @Order(14)
    void findClientByEmailAndPasswordWhenClientNotFound() {
        assertEquals(Optional.empty(),
                clientService.findClientByEmailAndPassword("bondar@gmail.com", "@Ali1234"));
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
    void clientSignUpWhenEmptyFiledExceptionThrown_thenAssertionSucceeds() {
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
    void clientSignUpWhenInvalidEmailExceptionThrown_thenAssertionSucceeds() {
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
    void clientSignUpWhenDuplicatedEmailExceptionThrown_thenAssertionSucceeds() {
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
    void clientSignUpWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
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
    @Order(12)
    void isClientEmailDuplicated() {
        assertEquals(true, clientService.isClientEmailDuplicated("ali@gmail.com"));
    }

    @Test
    @Order(11)
    void editClientPassword() throws NoSuchAlgorithmException {
        PasswordHash passwordHash = new PasswordHash();
        String password = "#Ali1234";
        String hashedPassword = passwordHash.createHashedPassword(password);
        clientService.editClientPassword(
                clientService.findClientByEmail("ali@gmail.com").get().getId(), password
        );
        assertEquals(hashedPassword, clientService.findClientByEmail("ali@gmail.com").get().getPassword());

    }

    @Test
    @Order(9)
    void editClientPasswordWhenNotFoundUserExceptionThrown_thenAssertionSucceeds() {
        assertThrows(NotFoundTheUserException.class, () -> {
            clientService.editClientPassword(0L, "#Ali1234");
        });
    }

    @Test
    @Order(10)
    void editClientPasswordWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
        assertThrows(InvalidPasswordException.class, () -> {
            clientService.editClientPassword(clientService.findClientByEmail("ali@gmail.com").get().getId(),
                    "li1234");
        });
    }
}