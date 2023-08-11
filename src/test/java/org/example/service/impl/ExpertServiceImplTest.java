package org.example.service.impl;

import org.example.command.ExpertSignUpCommand;
import org.example.command.OfferCommand;
import org.example.command.ServiceCommand;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;
import org.example.exception.*;
import org.example.repository.ExpertRepository;
import org.example.repository.OfferRepository;
import org.example.repository.OrderRepository;
import org.example.repository.SubServiceRepository;
import org.example.security.PasswordHash;
import org.example.service.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceImplTest {
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferService offerService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SubServiceRepository subServiceRepository;
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private OrderService orderService;

    @Test
    @Order(8)
    void findExpertByEmail() {
        assertNotNull(expertService.findExpertByEmail("expert@gmail.com").get());
    }

    @Test
    @Order(9)
    void findExpertByEmailAndPassword() {
        assertNotNull(expertService.findExpertByEmailAndPassword("expert@gmail.com", "@Expert1234")
                .get());
    }

    @Test
    @Order(6)
    void expertSignUp() throws IOException {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("@Expert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        expertService.expertSignUp(expertSignUpCommand);
        assertEquals(expertService.findExpertByEmail("expert@gmail.com").get().getEmail(),
                expertSignUpCommand.getEmail());
    }

    @Test
    @Order(1)
    void expertSignUpWhenEmptyFiledExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        adminService.addService(new ServiceCommand("testService"));
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("@Expert1234");
        assertThrows(EmptyFieldException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(2)
    void expertSignUpWhenInvalidEmailExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expertcom");
        expertSignUpCommand.setPassword("@Expert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        assertThrows(InvalidEmailException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(3)
    void expertSignUpWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("ert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        assertThrows(InvalidPasswordException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(7)
    void expertSignUpWhenDuplicatedEmailExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("@Expert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        assertThrows(DuplicatedEmailException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(5)
    void expertSignUpWhenImageFormatExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\1995669.png";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("@Expert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        assertThrows(ImageFormatException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(4)
    void expertSignUpWhenImageSizeExceptionThrown_thenAssertionSucceeds() {
        ExpertSignUpCommand expertSignUpCommand = new ExpertSignUpCommand();
        String imageAddress = "D:\\Pictures\\Sky2.jpg";
        File file = new File(imageAddress);
        expertSignUpCommand.setFirstName("expert");
        expertSignUpCommand.setLastName("expertian");
        expertSignUpCommand.setEmail("expert@gmail.com");
        expertSignUpCommand.setPassword("@Expert1234");
        expertSignUpCommand.setImageData(file);
        expertSignUpCommand.setService(serviceService.findServiceByName("testService").get());
        assertThrows(ImageSizeException.class, () -> {
            expertService.expertSignUp(expertSignUpCommand);
        });
    }

    @Test
    @Order(10)
    void isExpertEmailDuplicated() {
        assertTrue(expertService.isExpertEmailDuplicated("expert@gmail.com"));
    }

    @Test
    @Order(11)
    void findExpertsByUserStatus() {
        assertNotNull(expertService.findExpertsByUserStatus(UserStatus.NEW).get(0));
    }

    @Test
    @Order(13)
    void editExpertPassword() throws NoSuchAlgorithmException {
        PasswordHash passwordHash = new PasswordHash();
        String password = "#Expert1234";
        String hashedPassword = passwordHash.createHashedPassword(password);
        expertService.editExpertPassword(
                expertService.findExpertByEmail("expert@gmail.com").get().getId(), password
        );
        assertEquals(hashedPassword, expertService.findExpertByEmail("expert@gmail.com").get().getPassword());
    }

    @Test
    @Order(12)
    void editPasswordWhenUserNotFoundExceptionThrown_thenAssertionSucceed(){
        assertThrows(NotFoundTheUserException.class, () -> {
            expertService.editExpertPassword(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId() + 1, "#Expert1234"
            );
        });
    }

    @Test
    @Order(12)
    void editPasswordWhenInvalidPasswordExceptionThrown_thenAssertionSucceed() {
        assertThrows(InvalidPasswordException.class, () -> {
            expertService.editExpertPassword(
                    expertService.findExpertByEmail("expert@gmail.com").get().getId(), "ert1234"
            );
        });
    }

    @Test
    @Order(18)
    void createOffer() {
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerCommand.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerCommand.setOfferedPrice(12000);
        offerCommand.setOfferedStartTime(LocalTime.of(20,10));
        offerCommand.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerCommand.setExpertOfferedWorkDuration(2);
        expertService.createOffer(offerCommand);
        assertNotNull(expertService.findExpertByEmail("expert@gmail.com").get().getOfferList());
    }

    @Test
    @Order(13)
    void createOfferWhenEmptyFieldExceptionThrown_thenAssertionSucceed() {
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(null);
        assertThrows(EmptyFieldException.class, () -> {
            expertService.createOffer(offerCommand);
        });
    }

    @Test
    @Order(14)
    void createOfferWhenUserConfirmationExceptionThrown_thenAssertionSucceed() {
        SubService subService = new SubService();
        subService.setBasePrice(10000);
        subService.setDescription("testSubService");
        subServiceRepository.save(subService);
        org.example.entity.Order order = new org.example.entity.Order();
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setDescription("good");
        order.setClientOfferedPrice(10000);
        order.setSubService(subService);
        orderRepository.save(order);
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerCommand.setOrder(order);
        offerCommand.setOfferedPrice(8000);
        offerCommand.setOfferedStartTime(LocalTime.of(20,10));
        offerCommand.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerCommand.setExpertOfferedWorkDuration(2);
        assertThrows(UserConfirmationException.class, () -> {
            expertService.createOffer(offerCommand);
        });
    }


    @Test
    @Order(15)
    void createOfferWhenInvalidPriceExceptionThrown_thenAssertionSucceed() {
        Expert expert = expertService.findExpertByEmail("expert@gmail.com").get();
        expert.setUserStatus(UserStatus.CONFIRMED);
        expertRepository.save(expert);
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerCommand.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerCommand.setOfferedPrice(8000);
        offerCommand.setOfferedStartTime(LocalTime.of(20,10));
        offerCommand.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerCommand.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidPriceException.class, () -> {
            expertService.createOffer(offerCommand);
        });
    }

    @Test
    @Order(16)
    void createOfferWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerCommand.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerCommand.setOfferedPrice(12000);
        offerCommand.setOfferedStartTime(LocalTime.of(23,10));
        offerCommand.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerCommand.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidTimeException.class, () -> {
            expertService.createOffer(offerCommand);
        });
    }

    @Test
    @Order(17)
    void createOfferWhenInvalidDateExceptionThrown_thenAssertionSucceed() {
        OfferCommand offerCommand = new OfferCommand();
        offerCommand.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerCommand.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerCommand.setOfferedPrice(12000);
        offerCommand.setOfferedStartTime(LocalTime.of(20,10));
        offerCommand.setOfferedStartDate(LocalDate.of(2020, 9,5));
        offerCommand.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidDateException.class, () -> {
            expertService.createOffer(offerCommand);
        });
    }

}