package org.example.view;

import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
            System.out.println("""
                ********** Welcome to the HOME SERVICE PROVIDER **********
                Choose one of the Options below :
                1. Login as Client
                2. Login as Expert
                3. Login as Admin
                4. SignUp
                5. Exit
                """);
            int option = scanner.nextInt();
            if (option == 1 || option == 2 || option == 3 || option == 4 || option == 5) {
                switch (option) {
                    case 1:
                        ClientMenu clientMenu = new ClientMenu();
                        clientMenu.showClientLogin();
                        break;
                    case 2:
                        ExpertMenu expertMenu = new ExpertMenu();
                        expertMenu.showExpertLogin();
                        break;
                    case 3:
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.showAdminLogin();
                        break;
                    case 4:
                        SignUpMenu signUpMenu = new SignUpMenu();
                        signUpMenu.showMenu();
                    default:
                        break;
                }
            } else {
                System.out.println("invalid option !");
                showMenu();
            }
    }
}
