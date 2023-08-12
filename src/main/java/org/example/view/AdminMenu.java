package org.example.view;

import lombok.RequiredArgsConstructor;
import org.example.dto.ServiceDTO;
import org.example.dto.SubServiceDTO;
import org.example.entity.SubService;
import org.example.entity.users.Admin;
import org.example.entity.users.Expert;
import org.example.entity.users.enums.UserStatus;

import org.example.repository.ExpertRepository;
import org.example.repository.ServiceRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.AdminService;
import org.example.service.ExpertService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;


import java.util.Scanner;

@RequiredArgsConstructor
public class AdminMenu {

    private final Scanner scanner = new Scanner(System.in);

    ServiceService serviceService;
    ServiceRepository serviceRepository;

    SubServiceService subServiceService;
    SubServiceRepository subServiceRepository;

    ExpertService expertService;
    ExpertRepository expertRepository;

    AdminService adminService;

    Admin admin = new Admin();

    public void showAdminLogin() {
        System.out.println("***** ADMIN LOGIN *****");
        System.out.println("Enter your email :");
        String email = scanner.next();
        System.out.println("Enter your password :");
        String password = scanner.next();
        if (adminService.findAdminByEmailAndPassword(email, password).isPresent()) {
            admin = adminService.findAdminByEmailAndPassword(email, password).get();
            System.out.println("Welcome " + admin.getFirstName() + " " + admin.getLastName());
            showAdminMenu();
        } else {
            System.out.println("Invalid email or password !");
            showAdminLogin();
        }
    }

    public void showAdminMenu() {
        System.out.println("***** ADMIN PANEL *****");
        System.out.println("""
                Choose one of the options below :
                1. Add service
                2. Add sub service
                3. Add expert to sub service
                4. Remove expert from sub service
                5. Show services
                6. Show sub services
                7. Edit services
                8. Edit sub services
                9. Confirm new experts
                10. Back to main menu
                """);
        int option = scanner.nextInt();
        if (option == 1 || option == 2 || option == 3 || option == 4
                || option == 5 || option == 6 || option == 7 || option == 8 || option == 9 || option == 10) {
            if (option == 1) {
                System.out.println("Enter the name of the service :");
                String serviceName = scanner.next();
                ServiceDTO serviceCommand = new ServiceDTO();
                serviceCommand.setName(serviceName);
                try {
                    adminService.addService(serviceCommand);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 2) {
                System.out.println("Choose one of the services :");
                serviceRepository.findAll().forEach(service -> {
                    System.out.println(service.getId() + ". " + service.getName());
                });
                int serviceId = scanner.nextInt();
                System.out.println("Enter the description of the sub service :");
                String subServiceDescription = scanner.next();
                System.out.println("Enter base price :");
                double subServiceBasePrice = scanner.nextDouble();
                SubServiceDTO subServiceCommand = new SubServiceDTO();
                subServiceCommand.setService(serviceRepository.findById((long) serviceId).get());
                subServiceCommand.setDescription(subServiceDescription);
                subServiceCommand.setBasePrice(subServiceBasePrice);
                try {
                    adminService.addSubService(subServiceCommand);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 3) {
                System.out.println("Choose between experts :");
                serviceRepository.findAll().forEach(expert -> {
                    System.out.println(expert.getId() + " " + expert.toString());
                });
                int expertId = scanner.nextInt();
                Expert expert = expertRepository.findById((long) expertId).get();
                System.out.println("Choose between sub services :");
                subServiceRepository.findAll().forEach(subService -> {
                    System.out.println(subService.getId() + ". " + subService.getDescription());
                });
                int subServiceId = scanner.nextInt();
                SubService subService = subServiceRepository.findById((long) subServiceId).get();
                try {
                    adminService.addExpertToSubService((long) expertId, (long) subServiceId);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 4) {
                System.out.println("Choose between experts :");
                expertRepository.findAll().forEach(expert -> {
                    System.out.println(expert.getId() + " " + expert.toString());
                });
                int expertId = scanner.nextInt();
                Expert expert = expertRepository.findById((long) expertId).get();
                subServiceService.findByExpertId((long) expertId).forEach(subService -> {
                    System.out.println(subService.getId() + ", " + subService.getDescription());
                });
                System.out.println("Choose the sub service you want to remove :");
                int subServiceId = scanner.nextInt();
                SubService subService = subServiceRepository.findById((long) subServiceId).get();
                try {
                    adminService.removeExpertFromSubService((long) expertId, (long) subServiceId);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 5) {
                System.out.println("Here are services :");
                serviceRepository.findAll().forEach(service -> {
                    System.out.println(service.getId() + ". " + service.getName());
                });
                showAdminMenu();
            } else if (option == 6) {
                System.out.println("Here are sub services :");
                subServiceRepository.findAll().forEach(subService -> {
                    System.out.println(subService.getId() + ". " + subService.getDescription());
                });
                showAdminMenu();
            } else if (option == 7) {
                System.out.println("Choose ");
            } else if (option == 8) {
                System.out.println("Enter the id of sub service that you want to edit :");
                subServiceRepository.findAll().forEach(subService -> {
                    System.out.println(subService.getId() + ". " + subService.getDescription());
                });
                int subServiceId = scanner.nextInt();
                System.out.println("Enter new base price :");
                double newBasePrice = scanner.nextInt();
                System.out.println("Enter new description :");
                String newDescription = scanner.next();
                try {
                    adminService.editSubService((long) subServiceId, newBasePrice, newDescription);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 9) {
                System.out.println("Here are new experts :");
                try {
                    expertRepository.findExpertsByUserStatus(UserStatus.NEW).forEach(expert -> {
                        System.out.println(expert.getId() + ". " + expert.getFirstName() + " " + expert.getLastName()
                        );
                    });
                    System.out.println("Choose the id of the expert you want to confirm :");
                    int expertId = scanner.nextInt();
                    try {
                        adminService.editExpertStatus((long) expertId, UserStatus.CONFIRMED);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                showAdminMenu();
            } else if (option == 10) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.showMenu();
            }
        } else {
            System.out.println("invalid option !");
            showAdminMenu();
        }
    }
}
