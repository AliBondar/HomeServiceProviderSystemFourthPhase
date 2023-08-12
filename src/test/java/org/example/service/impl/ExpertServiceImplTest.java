package org.example.service.impl;

import org.example.dto.ExpertDTO;
import org.example.dto.OfferDTO;
import org.example.dto.ServiceDTO;
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
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("@Expert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        expertService.expertSignUp(expertDTO);
        assertEquals(expertService.findExpertByEmail("expert@gmail.com").get().getEmail(),
                expertDTO.getEmail());
    }

    @Test
    @Order(1)
    void expertSignUpWhenEmptyFiledExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        adminService.addService(new ServiceDTO("testService"));
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("@Expert1234");
        assertThrows(EmptyFieldException.class, () -> {
            expertService.expertSignUp(expertDTO);
        });
    }

    @Test
    @Order(2)
    void expertSignUpWhenInvalidEmailExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expertcom");
        expertDTO.setPassword("@Expert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        assertThrows(InvalidEmailException.class, () -> {
            expertService.expertSignUp(expertDTO);
        });
    }

    @Test
    @Order(3)
    void expertSignUpWhenInvalidPasswordExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("ert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        assertThrows(InvalidPasswordException.class, () -> {
            expertService.expertSignUp(expertDTO);
        });
    }

    @Test
    @Order(7)
    void expertSignUpWhenDuplicatedEmailExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\cat.jpg";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("@Expert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        assertThrows(DuplicatedEmailException.class, () -> {
            expertService.expertSignUp(expertDTO);
        });
    }

    @Test
    @Order(5)
    void expertSignUpWhenImageFormatExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\1995669.png";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("@Expert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        assertThrows(ImageFormatException.class, () -> {
            expertService.expertSignUp(expertDTO);
        });
    }

    @Test
    @Order(4)
    void expertSignUpWhenImageSizeExceptionThrown_thenAssertionSucceeds() {
        ExpertDTO expertDTO = new ExpertDTO();
        String imageAddress = "D:\\Pictures\\Sky2.jpg";
        File file = new File(imageAddress);
        expertDTO.setFirstName("expert");
        expertDTO.setLastName("expertian");
        expertDTO.setEmail("expert@gmail.com");
        expertDTO.setPassword("@Expert1234");
        expertDTO.setImageData(file);
        expertDTO.setService(serviceService.findServiceByName("testService").get());
        assertThrows(ImageSizeException.class, () -> {
            expertService.expertSignUp(expertDTO);
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
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerDTO.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerDTO.setOfferedPrice(12000);
        offerDTO.setOfferedStartTime(LocalTime.of(20,10));
        offerDTO.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerDTO.setExpertOfferedWorkDuration(2);
        expertService.createOffer(offerDTO);
        assertNotNull(expertService.findExpertByEmail("expert@gmail.com").get().getOfferList());
    }

    @Test
    @Order(13)
    void createOfferWhenEmptyFieldExceptionThrown_thenAssertionSucceed() {
        OfferDTO offerCommand = new OfferDTO();
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
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerDTO.setOrder(order);
        offerDTO.setOfferedPrice(8000);
        offerDTO.setOfferedStartTime(LocalTime.of(20,10));
        offerDTO.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerDTO.setExpertOfferedWorkDuration(2);
        assertThrows(UserConfirmationException.class, () -> {
            expertService.createOffer(offerDTO);
        });
    }


    @Test
    @Order(15)
    void createOfferWhenInvalidPriceExceptionThrown_thenAssertionSucceed() {
        Expert expert = expertService.findExpertByEmail("expert@gmail.com").get();
        expert.setUserStatus(UserStatus.CONFIRMED);
        expertRepository.save(expert);
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerDTO.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerDTO.setOfferedPrice(8000);
        offerDTO.setOfferedStartTime(LocalTime.of(20,10));
        offerDTO.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerDTO.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidPriceException.class, () -> {
            expertService.createOffer(offerDTO);
        });
    }

    @Test
    @Order(16)
    void createOfferWhenInvalidTimeExceptionThrown_thenAssertionSucceed() {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerDTO.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerDTO.setOfferedPrice(12000);
        offerDTO.setOfferedStartTime(LocalTime.of(23,10));
        offerDTO.setOfferedStartDate(LocalDate.of(2023, 9,5));
        offerDTO.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidTimeException.class, () -> {
            expertService.createOffer(offerDTO);
        });
    }

    @Test
    @Order(17)
    void createOfferWhenInvalidDateExceptionThrown_thenAssertionSucceed() {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setExpert(expertService.findExpertByEmail("expert@gmail.com").get());
        offerDTO.setOrder(orderService.findOrdersByOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER).get(0));
        offerDTO.setOfferedPrice(12000);
        offerDTO.setOfferedStartTime(LocalTime.of(20,10));
        offerDTO.setOfferedStartDate(LocalDate.of(2020, 9,5));
        offerDTO.setExpertOfferedWorkDuration(2);
        assertThrows(InvalidDateException.class, () -> {
            expertService.createOffer(offerDTO);
        });
    }

}