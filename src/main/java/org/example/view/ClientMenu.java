package org.example.view;

import lombok.RequiredArgsConstructor;
import org.example.command.OrderCommand;
import org.example.entity.Order;
import org.example.entity.Service;
import org.example.entity.SubService;
import org.example.entity.enums.OrderStatus;
import org.example.entity.users.Client;
import org.example.repository.ClientRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ServiceRepository;
import org.example.repository.SubServiceRepository;
import org.example.service.ClientService;
import org.example.service.OrderService;
import org.example.service.ServiceService;
import org.example.service.SubServiceService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ClientMenu {

    ClientService clientService;
    ClientRepository clientRepository;
    ServiceService serviceService;
    ServiceRepository serviceRepository;
    SubServiceService subServiceService;
    SubServiceRepository subServiceRepository;
    OrderService orderService ;
    OrderRepository orderRepository;
    private final Scanner scanner = new Scanner(System.in);

    Client client = new Client();

    public void showClientLogin() {
        System.out.println("***** CLIENTS LOGIN *****");
        System.out.println("Enter your email :");
        String email = scanner.next();
        System.out.println("Enter your password :");
        String password = scanner.next();
        if (clientService.findClientByEmailAndPassword(email, password).isPresent()) {
            client = clientService.findClientByEmailAndPassword(email, password).get();
            System.out.println("Welcome " + client.getFirstName() + " " + client.getLastName());
            showClientMenu();
        } else {
            System.out.println("invalid username or password !");
            showClientLogin();
        }
    }

    public void showClientMenu() {
        System.out.println("***** CLIENTS PANEL *****");
        System.out.println("""
                Choose one of the options below :
                1. New Order
                2. Show Order History
                3. Edit Password
                4. Back to Main Menu
                """);
        int option = scanner.nextInt();
        if (option == 1 || option == 2 || option == 3 || option == 4) {
            switch (option) {
                case 1 -> {
                    System.out.println("***** NEW ORDER PANEL *****");
                    System.out.println("Enter the ID of the service that you want :");
                    List<Service> serviceList = serviceRepository.findAll();
                    serviceList.forEach(service -> {
                        System.out.println(service.getId() + ". " + service.getName());
                    });
                    int serviceId = scanner.nextInt();
                    System.out.println("All sub services for " + serviceRepository.findById((long) serviceId).get().getName() + " :");
                    List<SubService> subServiceList = subServiceService.findSubServicesByServiceId((long) serviceId);
                    subServiceList.forEach(subService -> {
                        System.out.println(subService.getId() + ". " + subService.getDescription() + ", Base Price : " + subService.getBasePrice());
                    });
                    System.out.println("Enter the sub service's ID that you want : ");
                    int subServiceId = scanner.nextInt();
                    SubService subService = subServiceRepository.findById((long) subServiceId).get();
                    while (true) {
                        if (subServiceId != 0) {
                            System.out.println("Enter the sub service's ID that you want , Enter 0 for exit : ");
                            subServiceId = scanner.nextInt();
                            if (subServiceId != 0) {

                            }
                        } else {
                            break;
                        }
                    }
                    System.out.println("Describe what you want :");
                    String description = scanner.next();
                    System.out.println("Enter your offered price :");
                    double offeredPrice = scanner.nextDouble();
                    System.out.println("Enter your offered work duration :");
                    int offeredWordDuration = scanner.nextInt();
                    OrderCommand orderCommand = new OrderCommand();
                    orderCommand.setClient(client);
                    orderCommand.setDescription(description);
                    orderCommand.setLocalDate(LocalDate.of(2023, 11, 2));
                    orderCommand.setLocalTime(LocalTime.of(20,30));
                    orderCommand.setSubService(subService);
                    orderCommand.setClientOfferedPrice(offeredPrice);
                    orderCommand.setClientOfferedWorkDuration(offeredWordDuration);
                    try {
                        orderService.createOrder(orderCommand);
                    }catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                    showClientMenu();
                }
                case 2 -> {
                    System.out.println("***** ORDER HISTORY PANEL *****");
                    if (orderService.findOrdersByClientId(client.getId()).size() != 0) {
                        System.out.println(orderService.findOrdersByClientId(client.getId()));
                    } else {
                        System.out.println("you haven't made any order yet !");
                        showClientMenu();
                    }
                }
                case 3 -> {
                    System.out.println("***** EDIT PASSWORD *****");
                    System.out.println("Enter new password :");
                    String newPassword = scanner.next();
                    try {
                        clientService.editClientPassword(client.getId(), newPassword);
                    }catch (RuntimeException e){
                        e.printStackTrace();
                    }
                    showClientMenu();
                }
                case 4 -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.showMenu();
                }
            }
        } else {
            System.out.println("invalid option !");
            showClientMenu();
        }
    }

    public void createOrder(String description, LocalDate localDate, Long client_id, Long service_id, SubService subService) {
        Order order = new Order();
        order.setDescription(description);
        order.setLocalDate(localDate);
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_OFFER);
        order.setClient(clientRepository.findById(client_id).get());
        order.setSubService(subService);
        orderRepository.save(order);
    }
}
