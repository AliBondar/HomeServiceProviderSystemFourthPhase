package org.example.view;

import org.apache.commons.io.FileUtils;
import org.example.dto.ClientDTO;
import org.example.dto.ExpertDTO;
import org.example.entity.Service;

import org.example.repository.ClientRepository;
import org.example.repository.ExpertRepository;
import org.example.repository.ServiceRepository;
import org.example.service.ClientService;
import org.example.service.ExpertService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


@Component
public class SignUpMenu {

    ClientService clientService ;
    ClientRepository clientRepository;
    ExpertService expertService;
    ExpertRepository expertRepository;
    ServiceService serviceService;
    ServiceRepository serviceRepository;
    SubServiceService subServiceService;

//    @Autowired
//    public SignUpMenu(ClientService clientService, ClientRepository clientRepository, ExpertService expertService, ExpertRepository expertRepository, ServiceService serviceService, ServiceRepository serviceRepository, SubServiceService subServiceService) {
//        this.clientService = clientService;
//        this.clientRepository = clientRepository;
//        this.expertService = expertService;
//        this.expertRepository = expertRepository;
//        this.serviceService = serviceService;
//        this.serviceRepository = serviceRepository;
//        this.subServiceService = subServiceService;
//    }

    private final Scanner scanner = new Scanner(System.in);

    public SignUpMenu() {

    }

    public void showMenu() {
        System.out.println("***** SIGN UP PANEL *****");
        System.out.println("""
                Choose type of registration :
                1. Client
                2. Expert
                3. Back to Main Menu
                """);
        int registrationType = scanner.nextInt();
        if(registrationType == 1 || registrationType == 2 || registrationType == 3){
            switch (registrationType){
                case 1 -> {
                    System.out.println("***** CLIENT SIGN UP PANEL *****");
                    System.out.println("Enter first name :");
                    String firstName = scanner.next();
                    System.out.println("Enter last name :");
                    String lastName = scanner.next();
                    System.out.println("Enter email :");
                    String email = scanner.next();
                    System.out.println("Enter password :");
                    String password = scanner.next();
                    ClientDTO clientSignUpCommand = new ClientDTO();
                    clientSignUpCommand.setFirstName(firstName);
                    clientSignUpCommand.setLastName(lastName);
                    clientSignUpCommand.setEmail(email);
                    clientSignUpCommand.setPassword(password);
                    try {
                        clientService.clientSignUp(clientSignUpCommand);
                        ClientMenu clientMenu = new ClientMenu();
                        clientMenu.showClientLogin();
                    }catch (RuntimeException e){
                        e.printStackTrace();
                        showMenu();
                    } catch (jakarta.mail.SendFailedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("***** EXPERT SIGN UP PANEL *****");
                    System.out.println("Enter first name :");
                    String firstName = scanner.next();
                    System.out.println("Enter last name :");
                    String lastName = scanner.next();
                    System.out.println("Enter email :");
                    String email = scanner.next();
                    System.out.println("Enter password :");
                    String password = scanner.next();
                    System.out.println("Choose a service :");
                    serviceService.findAll().forEach(service -> {
                        System.out.println(service.getId() + ". " + service.getName());
                    });
                    int serviceId = scanner.nextInt();
                    System.out.println("Insert image :");
                    String imageAddress = scanner.next();
                    Service service = serviceRepository.findById((long) serviceId).get();
                    ExpertDTO expertSignUpCommand = new ExpertDTO();
                    expertSignUpCommand.setFirstName(firstName);
                    expertSignUpCommand.setLastName(lastName);
                    expertSignUpCommand.setEmail(email);
                    expertSignUpCommand.setPassword(password);
                    expertSignUpCommand.setService(service);
                    try {
                        String imageStr = "D:\\Pictures\\cat.jpg";
                        File file = new File(imageAddress);
                        byte[] imageContent = FileUtils.readFileToByteArray(file);
                        expertSignUpCommand.setImageData(imageStr);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        expertService.expertSignUp(expertSignUpCommand);
                    }catch (RuntimeException | IOException e){
                        e.printStackTrace();
                        showMenu();
                    }
                    ExpertMenu expertMenu = new ExpertMenu();
                    expertMenu.showExpertLogin();
                }
                case 3 -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.showMenu();
                }
            }
        }
    }
}
