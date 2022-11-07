package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService = new AccountService(API_BASE_URL);
    private TransferService transferService = new TransferService(API_BASE_URL);
    private UserService userService = new UserService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        userService.setCurrentUser(currentUser.getUser());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            userService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
            accountService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        System.out.println("Your current balance is: $" + accountService.getBalance(userService.getAccountId(currentUser.getUser().getId())));
		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        Transfer[] transfers = transferService.viewTransferHistory();
        System.out.println("Your previous transactions: ");
        if (transfers != null) {
            for (Transfer transfer : transfers) {
                System.out.println(transfer);
            }
        }
		
	}

	private void viewPendingRequests() {
        Transfer[] transfers = transferService.viewPendingTransfers();
        System.out.println("Current pending transactions: ");
        if (transfers != null) {
            for (Transfer transfer : transfers) {
                System.out.println(transfer);
            }
        }

		// TODO Auto-generated method stub

	}

	private void sendBucks() {
        // TODO Auto-generated method stub

        Map<Integer, String> users = userService.listUsers();
        for (Map.Entry<Integer, String> user : users.entrySet()) {
            System.out.printf("%-5s %-20s \n", user.getKey(), user.getValue());
        }
        System.out.println("Please choose one from user id's: ");
        Scanner sc = new Scanner(System.in);
        int userId = sc.nextInt();
        if (userId != currentUser.getUser().getId()) {
            BigDecimal amount = consoleService.promptForBigDecimal("Please choose an amount in decimal form (ex. 20.49):");
            if(amount.compareTo(BigDecimal.valueOf(0))==1 ) {
                Transfer transfer = new Transfer();
                transfer.setTransferTypeId(2);
                transfer.setAmount(amount);
                transfer.setAccountTo(userService.getAccountId(userId));
                transfer.setAccountFrom(userService.getAccountId(currentUser.getUser().getId()));
                transfer.setTransferStatusId(2);
                transferService.sendTransfer(transfer);
                System.out.println("Transfer sent!");
            } else {
                System.out.println("Invalid, amount must be greater than zero");
            }
        } else {
            System.out.println("Invalid, you cannot send money to yourself");
        }
    }



	private void requestBucks() {
		// TODO Auto-generated method stub

        Map<Integer, String> users = userService.listUsers();
        for(Map.Entry<Integer, String> user: users.entrySet()) {
            System.out.printf("%-5s %-20s \n", user.getKey(), user.getValue());
        }
        System.out.println("Please choose one from user id's: ");
        Scanner sc = new Scanner(System.in);
        int userId = sc.nextInt();
        if (userId != currentUser.getUser().getId()) {
            BigDecimal amount = consoleService.promptForBigDecimal("Please choose an amount in decimal form (ex. 20.49):");
            if(amount.compareTo(BigDecimal.valueOf(0))==1 ) {
                Transfer transfer = new Transfer();
                transfer.setTransferTypeId(2);
                transfer.setAmount(amount);
                transfer.setAccountTo(userService.getAccountId(currentUser.getUser().getId()));
                transfer.setAccountFrom(userService.getAccountId(userId));
                transfer.setTransferStatusId(2);
                transferService.sendTransfer(transfer);
                System.out.println("Transfer sent!");
            } else {
                System.out.println("Invalid, amount must be greater than zero");
            }
        } else {
            System.out.println("Invalid, you cannot send money to yourself");
        }
    }




}
