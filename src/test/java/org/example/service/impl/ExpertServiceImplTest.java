package org.example.service.impl;

import org.example.command.ExpertSignUpCommand;
import org.example.command.ServiceCommand;
import org.example.exception.*;
import org.example.service.ExpertService;
import org.example.service.ServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpertServiceImplTest {
    @Autowired
    private ExpertService expertService;
    @Autowired
    private ServiceService serviceService;

    @Test
    void findExpertByEmail() {
    }

    @Test
    void findExpertByEmailAndPassword() {
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
        serviceService.addService(new ServiceCommand("testService"));
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
    void isExpertEmailDuplicated() {
    }

    @Test
    void findExpertsByUserStatus() {
    }

    @Test
    void editExpertPassword() {
    }
}