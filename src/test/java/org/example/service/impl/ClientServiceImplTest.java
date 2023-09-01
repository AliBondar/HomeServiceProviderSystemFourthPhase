package org.example.service.impl;

import org.example.dto.ClientDTO;
import org.example.dto.OrderDTO;
import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.entity.Offer;
import org.example.entity.enums.OrderStatus;
import org.example.exception.*;
import org.example.repository.ClientRepository;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.security.PasswordHash;
import org.example.service.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.sql.Time;
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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;

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
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("ali");
        clientDTO.setLastName("bon");
        clientDTO.setEmail("ali@gmail.com");
        clientDTO.setPassword("@Ali1234");
        clientService.clientSignUp(clientDTO);
        assertEquals(clientService.findClientByEmail(clientDTO.getEmail()).get().getEmail(),
                clientDTO.getEmail());
    }

    @Order(1)
    @Test
    void clientSignUpWhenEmptyFiledExceptionThrown_thenAssertionSucceeds() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("ali");
        clientDTO.setEmail("ali@gmail.com");
        clientDTO.setPassword("@Ali1234");
        assertThrows(EmptyFieldException.class, () -> {
            clientService.clientSignUp(clientDTO);
        });
    }

    @Order(2)
    @Test
    void clientSignUpWhenInvalidEmailExceptionThrown_thenAssertionSucceeds() {
        ClientDTO clientSignUpCommand = new ClientDTO();
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
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("ali");
        clientDTO.setLastName("bon");
        clientDTO.setEmail("ali@gmail.com");
        clientDTO.setPassword("@Ali1234");
        assertThrows(DuplicatedEmailException.class, () -> {
            clientService.clientSignUp(clientDTO);
        });
    }

    @Order(3)
    @Test
    void clientSignUpWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("ali");
        clientDTO.setLastName("bon");
        clientDTO.setEmail("ali@gmail.com");
        clientDTO.setPassword("li");
        assertThrows(InvalidPasswordException.class, () -> {
            clientService.clientSignUp(clientDTO);
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
        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderDTO.setLocalDate(LocalDate.of(2023, 12, 5));
//        orderDTO.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderDTO.setLocalTime(LocalTime.of(20, 30));
        orderDTO.setClientOfferedPrice(25000);
        orderDTO.setClientOfferedWorkDuration(2);
        orderDTO.setDescription("nice");
        clientService.createOrder(orderDTO);
        assertNotNull(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0));
    }

    @Test
    @Order(15)
    void createOrderWhenEmptyFieldExceptionThrown_thenAssertionSucceed() {
        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderDTO.setLocalTime(LocalTime.now());
        assertThrows(EmptyFieldException.class, () -> {
            clientService.createOrder(orderDTO);
        });
    }

    @Test
    @Order(16)
    void createOrderWhenInvalidDateExceptionThrown_thenAssertionSucceed() {
        adminService.addService(new ServiceDTO("testServiceForClient"));
        adminService.addSubService(new SubServiceDTO(
                15000, "testSubServiceForClient", serviceService.findServiceByName("testServiceForClient").get()
        ));
        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderDTO.setLocalDate(LocalDate.of(2022, 4, 6));
//        orderDTO.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderDTO.setLocalTime(LocalTime.of(20, 30));
        orderDTO.setClientOfferedPrice(25000);
        orderDTO.setClientOfferedWorkDuration(2);
        orderDTO.setDescription("nice");
        assertThrows(InvalidDateException.class, () -> {
            clientService.createOrder(orderDTO);
        });
    }

    @Test
    @Order(17)
    void createOrderWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderDTO.setLocalDate(LocalDate.of(2023, 12, 5));
//        orderDTO.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderDTO.setLocalTime(LocalTime.of(23, 30));
        orderDTO.setClientOfferedPrice(25000);
        orderDTO.setClientOfferedWorkDuration(2);
        orderDTO.setDescription("nice");
        assertThrows(InvalidTimeException.class, () -> {
            clientService.createOrder(orderDTO);
        });
    }

    @Test
    @Order(18)
    void createOrderWhenInvalidPriceExceptionThrown_thenAssertionSucceed() {
        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setClient(clientService.findClientByEmail("ali@gmail.com").get());
        orderDTO.setLocalDate(LocalDate.of(2023, 12, 5));
//        orderDTO.setSubService(subServiceService.findSubServiceByDescription("testSubServiceForClient").get());
        orderDTO.setLocalTime(LocalTime.of(20, 30));
        orderDTO.setClientOfferedPrice(1000);
        orderDTO.setClientOfferedWorkDuration(2);
        orderDTO.setDescription("nice");
        assertThrows(InvalidPriceException.class, () -> {
            clientService.createOrder(orderDTO);
        });
    }

    @Test
    @Order(22)
    void acceptOffer() {
        Offer offer = new Offer();
        offer.setAccepted(false);
        offer.setOrder(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0));
        offer.setOfferedPrice(20000);
        offer.setOfferedStartDate(LocalDate.of(2023, 12, 6));
        offerRepository.save(offer);
        clientService.acceptOffer(offer);
        assertEquals(OrderStatus.WAITING_FOR_EXPERT_ARRIVES, orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getOrderStatus());
        assertTrue(offerService.findAcceptedOfferByOrderId(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId()).get().isAccepted());

    }

    @Test
    @Order(26)
    void changeOrderStatusToStarted() {
        clientService.changeOrderStatusToStarted(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId());
        assertEquals(OrderStatus.STARTED, orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getOrderStatus());
    }

    @Test
    @Order(20)
    void changeOrderStatusToStartedWhenNotFoundTheOrderExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheOrderException.class, () -> {
            clientService.changeOrderStatusToStarted(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId() + 1);
        });
    }

    @Test
    @Order(21)
    void changeOrderStatusToStartedWhenNotFoundTheOfferExceptionThrown_thenAssertionSucceed() {
        assertThrows(NotFoundTheOfferException.class, () -> {
            clientService.changeOrderStatusToStarted(
                    orderService.findOrdersByClientId(
                            clientService.findClientByEmail("ali@gmail.com").get().getId()
                    ).get(0).getId()
            );
        });
    }

    @Test
    @Order(23)
    void changeOrderStatusToStartedWhenInvalidDateExceptionThrown_thenAssertionSucceed() {
        assertThrows(InvalidDateException.class, () -> {
            clientService.changeOrderStatusToStarted(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId());
        });
    }

    @Test
    @Order(24)
    void changeOrderStatusToStartedWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        Offer offer = offerService.findAcceptedOfferByOrderId(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId()).get();
        offer.setOfferedStartDate(LocalDate.of(2023, 2, 2));
        offer.setOfferedStartTime(Time.valueOf(LocalTime.of(23, 59)));
        offerRepository.save(offer);
        assertThrows(InvalidTimeException.class, () -> {
            clientService.changeOrderStatusToStarted(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId());
        });
        offer.setOfferedStartTime(Time.valueOf(LocalTime.of(8, 0)));
        offerRepository.save(offer);
    }

    @Test
    @Order(25)
    void changeOrderStatusToDoneWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        assertThrows(InvalidTimeException.class, () -> {
            clientService.changeOrderStatusToDone(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId());
        });
    }

    @Test
    @Order(27)
    void changeOrderStatusToDone() {
        clientService.changeOrderStatusToDone(orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getId());
        assertEquals(OrderStatus.DONE, orderService.findOrdersByClientId(clientService.findClientByEmail("ali@gmail.com").get().getId()).get(0).getOrderStatus());
    }

    @Test
    void calculateHour(){
        System.out.println(clientService.calculateDuration(LocalTime.of(17, 30)));
    }
}