package org.example.view;

import lombok.RequiredArgsConstructor;
import org.example.entity.users.Expert;
import org.example.service.AdminService;
import org.example.service.ExpertService;
import org.example.service.OrderService;
import org.example.service.SubServiceService;


import java.util.Scanner;

@RequiredArgsConstructor
public class ExpertMenu {
    private final Scanner scanner = new Scanner(System.in);

    ExpertService expertService;

    SubServiceService subServiceService;

    OrderService orderService;

    AdminService adminService;

    Expert expert = new Expert();



    public void showExpertLogin() {
        System.out.println("***** EXPERTS LOGIN *****");
        System.out.println("Enter your email :");
        String email = scanner.next();
        System.out.println("Enter your password :");
        String password = scanner.next();
        if (expertService.findExpertByEmailAndPassword(email, password).isPresent()) {
            expert = expertService.findExpertByEmailAndPassword(email, password).get();
            System.out.println("Welcome " + expert.getFirstName() + " " + expert.getLastName());
            showExpertMenu();
        } else {
            System.out.println("invalid username or password !");
            showExpertLogin();
        }
    }

    public void showExpertMenu() {
        System.out.println("***** EXPERTS PANEL *****");
        System.out.println("""
                Choose of the options below :
                1. Show Orders related to my service
                2. Choose sub service
                3. Show accepted orders
                4. Edit Password
                5. Back to Main menu
                """);
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> {
                System.out.println("Here are your sub services, Which one do you choose for work ?");
                subServiceService.findByExpertId(expert.getId()).forEach(subService -> {
                    System.out.println(subService.getId() + ". " + subService.getDescription());
                });
                int subServiceId = scanner.nextInt();
                orderService.findNewOrdersBySubServiceId((long) subServiceId).forEach(order -> {
                    System.out.println(order.getId() + ". " + order.getDescription()
                            + ", " + order.getLocalDate() + ", " + order.getOrderStatus());
                });
            }
            case 2 -> {
                System.out.println("Here are sub services in your service, Which one do you choose :");
                subServiceService.findSubServicesByServiceId(expert.getService().getId()).forEach(subService -> {
                    System.out.println(subService.getId() + ". " + subService.getDescription());
                });
                int subServiceId = scanner.nextInt();
                try {
                    adminService.addExpertToSubService(expert.getId(), (long) subServiceId);
                }catch (RuntimeException e){
                    e.printStackTrace();
                }
                showExpertMenu();
            }
            case 4 -> {
                System.out.println("Enter new password :");
                String newPassword = scanner.next();
                try {
                    expertService.editExpertPassword(expert.getId(), newPassword);
                }catch (RuntimeException e){
                    e.printStackTrace();
                }
                showExpertMenu();
            }
            case 5 -> {
                MainMenu mainMenu = new MainMenu();
                mainMenu.showMenu();
            }
            default -> {
                System.out.println("invalid option !");
                showExpertMenu();
            }
        }

    }
}
