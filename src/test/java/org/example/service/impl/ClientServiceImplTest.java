package org.example.service.impl;

import org.example.command.ClientSignUpCommand;
import org.example.command.OrderCommand;
import org.example.command.ServiceCommand;
import org.example.command.SubServiceCommand;
import org.example.entity.Offer;
import org.example.entity.enums.OrderStatus;
import org.example.exception.*;
import org.example.repository.OfferRepository;
import org.example.security.PasswordHash;
import org.example.service.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceImplTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private SubServiceService subServiceService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;

    @Test
    @Order(8)
    void findClientByEmail() {
        assertNotNull(clientService.findClientByEmail("ali@gmail.com").get());
    }

    @Test
    @Order(13)
    void findClientByEmailWhenClientNotFound() {
        assertEquals(Optional.empty(), clientService.findClientByEmail("bondar@gmail.com"));
    }

    @Order(6)
    @Test
    void findClientByEmailAndPassword() {
        assertNotNull(clientService.findClientByEmailAndPassword("ali@gmail.com", "@Ali1234").get());
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
        assertThrows(InvalidEmailException.class, () -> {
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
        assertTrue(clientService.isClientEmailDuplicated("ali@gmail.com"));
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

    @Test
    @Order(19)
    void createOrder() {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderCommand.setLocalDate(LocalDate.of(2023, 12, 5));
        orderCommand.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderCommand.setLocalTime(LocalTime.of(20, 30));
        orderCommand.setClientOfferedPrice(25000);
        orderCommand.setClientOfferedWorkDuration(2);
        orderCommand.setDescription("nice");
        clientService.createOrder(orderCommand);
        assertNotNull(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0));
    }

    @Test
    @Order(15)
    void createOrderWhenEmptyFieldExceptionThrown_thenAssertionSucceed() {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderCommand.setLocalTime(LocalTime.now());
        assertThrows(EmptyFieldException.class, () -> {
            clientService.createOrder(orderCommand);
        });
    }

    @Test
    @Order(16)
    void createOrderWhenInvalidDateExceptionThrown_thenAssertionSucceed() {
        adminService.addService(new ServiceCommand("testServiceForClient"));
        adminService.addSubService(new SubServiceCommand(
                15000, "testSubServiceForClient", serviceService.findServiceByName("testServiceForClient").get()
        ));
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderCommand.setLocalDate(LocalDate.of(2022, 4, 6));
        orderCommand.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderCommand.setLocalTime(LocalTime.of(20, 30));
        orderCommand.setClientOfferedPrice(25000);
        orderCommand.setClientOfferedWorkDuration(2);
        orderCommand.setDescription("nice");
        assertThrows(InvalidDateException.class, () -> {
            clientService.createOrder(orderCommand);
        });
    }

    @Test
    @Order(17)
    void createOrderWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderCommand.setLocalDate(LocalDate.of(2023, 12, 5));
        orderCommand.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderCommand.setLocalTime(LocalTime.of(23, 30));
        orderCommand.setClientOfferedPrice(25000);
        orderCommand.setClientOfferedWorkDuration(2);
        orderCommand.setDescription("nice");
        assertThrows(InvalidTimeException.class, () -> {
            clientService.createOrder(orderCommand);
        });
    }

    @Test
    @Order(18)
    void createOrderWhenInvalidPriceExceptionThrown_thenAssertionSucceed() {
        OrderCommand orderCommand = new OrderCommand();
        orderCommand.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderCommand.setLocalDate(LocalDate.of(2023, 12, 5));
        orderCommand.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderCommand.setLocalTime(LocalTime.of(20, 30));
        orderCommand.setClientOfferedPrice(1000);
        orderCommand.setClientOfferedWorkDuration(2);
        orderCommand.setDescription("nice");
        assertThrows(InvalidPriceException.class, () -> {
            clientService.createOrder(orderCommand);
        });
    }

    @Test
    @Order(20)
    void acceptOffer() {
        Offer offer = new Offer();
        offer.setAccepted(false);
        offer.setOrder(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0));
        offer.setOfferedPrice(20000);
        offerRepository.save(offer);
        clientService.acceptOffer(offer);
        assertEquals(OrderStatus.WAITING_FOR_EXPERT_ARRIVES, orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getOrderStatus());
        assertTrue(offerService.findAcceptedOfferByOrderId(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId()).get().isAccepted());

    }

    @Test
    void changeOrderStatusToStarted() {

    }
}