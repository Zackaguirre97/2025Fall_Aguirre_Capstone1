package com.pluralsight;

import java.util.Random;
import java.util.Scanner;

import static com.pluralsight.Account.printCenteredTitle;

public class Controller {
    // *** Props ***
    private Scanner sc;
    private Account account;
    private FileManager fileManager;
    private static int TOTAL_WIDTH = 105;

    // *** Const ***
    public Controller() {
        this.sc = new Scanner(System.in);
        this.account = new Account();
        this.fileManager = new FileManager();
        this.account.setTransactions(fileManager.getTransactionsFromFile());
    }

    // *** Methods ***
    // Print statements for the main menu/starting loop
    public void showMainMenu() {
        // Display the main menu.
        System.out.println(); // Add a blank line before the menu
        System.out.println("*".repeat(TOTAL_WIDTH));
        printCenteredTitle("Main Menu", TOTAL_WIDTH, " ");
        System.out.println("*".repeat(TOTAL_WIDTH));

        System.out.println("D. Add Deposit");
        System.out.println("P. Make Payment");
        System.out.println("L. View Ledger");
        System.out.println("X. Exit");

        System.out.println("-".repeat(TOTAL_WIDTH));
        System.out.print("Select an option: ");
    }


    // Starting menu/page. Shows the main menu
    public void start() {
        boolean runAgain = true;
        // Loop until the user chooses to exit
        while(runAgain) {
            showAppTitle(TOTAL_WIDTH);
            // Calculate the balance.
            account.calculateBalance();
            // Call/Display the main menu
            showMainMenu();
            // Each loop reset main menu choice input variable.
            String mainMenuChoice = sc.nextLine().trim();
            // Now validate the input and direct the program to the next relevant step.
            switch(mainMenuChoice.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    handleLedgerMenu();
                    break;
                case "X":
                    System.out.println("Thank you for using our app! Goodbye.");
                    runAgain = false;
                    break;
                default:
                    System.out.println("Enter D, P, L, or X.");
                    break;
            }
        }
    }

    // Prompts the user for deposit data, create a new transaction, add the transaction to the account.
    public void addDeposit() {
        boolean runAgain = true;
        while (runAgain) {
            showAppTitle(TOTAL_WIDTH);
            System.out.println("=".repeat(TOTAL_WIDTH));
            printCenteredTitle("Make Deposit", TOTAL_WIDTH, " ");
            System.out.println("=".repeat(TOTAL_WIDTH));
            System.out.print("Enter the deposit amount: ");
            String depositAmountInput = sc.nextLine().trim();
            // Once verifying that the depositAmount is a double, continue.
            if (isDouble(depositAmountInput)) {
                double depositAmount = Double.parseDouble(depositAmountInput);
                String[] randomDepositInfo = getRandomDepositInfo();
                String depositDescription = randomDepositInfo[0];
                String depositVendor = randomDepositInfo[1];
                Transaction newDeposit = new Transaction(depositDescription, depositVendor, depositAmount);
                account.addTransaction(newDeposit);
                fileManager.updateTransactionsFile(account.getTransactions());
                System.out.println("Confirmation: Deposit completed");
                runAgain = false;
            }
            else {
                System.out.println("Please enter a double (i.e. 26.3)");
            }
        }
    }

    // Prompt the user for payment data, create a new transaction, add the transaction to the account.
    public void makePayment() {
        boolean runAgain = true;
        while (runAgain) {
            showAppTitle(TOTAL_WIDTH);
            System.out.println("=".repeat(TOTAL_WIDTH));
            printCenteredTitle("Make Payment", TOTAL_WIDTH, " ");
            System.out.println("=".repeat(TOTAL_WIDTH));
            System.out.print("Enter the payment amount: ");
            String paymentAmountInput = sc.nextLine().trim();
            // Verify that the amount entered is a valid double
            if (isDouble(paymentAmountInput)) {
                double paymentAmount = Double.parseDouble(paymentAmountInput);
                System.out.print("Enter the description: ");
                String paymentDescription = sc.nextLine().trim();
                System.out.print("Enter the vendor name: ");
                String paymentVendor = sc.nextLine().trim();
                Transaction newPayment = new Transaction(paymentDescription, paymentVendor, (paymentAmount * -1));
                account.addTransaction(newPayment);
                fileManager.updateTransactionsFile(account.getTransactions());
                System.out.println("Confirmation: Payment completed");
                runAgain = false;
            } // If the value entered was not a double
            else {
                System.out.println("Please enter a double (i.e. 26.3)");
            }
        }
    }

    // Handles the display of the ledger menu
    public void displayLedgerMenu() {
        System.out.println("=".repeat(TOTAL_WIDTH));
        printCenteredTitle("Ledger Menu", TOTAL_WIDTH, " ");
        System.out.println("=".repeat(TOTAL_WIDTH));
        System.out.println("A. All Transactions");
        System.out.println("D. Deposits Only");
        System.out.println("P. Payments Only");
        System.out.println("R. Reports");
        System.out.println("H. Home");
        // Prompt the user to enter a choice from the above menu.
        System.out.print("Select an option: ");
    }

    // Handles the ledger page logic/flow
    public void handleLedgerMenu() {
        boolean runAgain = true;
        while (runAgain) {
            showAppTitle(TOTAL_WIDTH);
            displayLedgerMenu();
            String ledgerMenuChoice = sc.nextLine().trim();
            switch(ledgerMenuChoice.toUpperCase()) {
                case "A":
                case "D":
                case "P":
                    if (account.getReport(ledgerMenuChoice, TOTAL_WIDTH)) {
                        break;
                    }
                    else {
                        if (ledgerMenuChoice.equalsIgnoreCase("A")) {
                            System.out.println("No transactions in the account.");
                        }
                        else if (ledgerMenuChoice.equalsIgnoreCase("D")) {
                            System.out.println("No deposits in the account.");
                        } // If you make it this far, ledgerMenuChoice is for sure P
                        else {
                            System.out.println("No payments in the account.");
                        }
                    }
                    break;
                case "R":
                    handleReportsMenu();
                    break;
                case "H":
                    runAgain = false;
                    break;
            }
        }
    }

    // Handles the display of the reports menu
    public void showReportsMenu() {
        System.out.println("=".repeat(TOTAL_WIDTH));
        printCenteredTitle("Reports Menu", TOTAL_WIDTH, " ");
        System.out.println("=".repeat(TOTAL_WIDTH));
        System.out.println("1. Month To Date");
        System.out.println("2. Previous Month");
        System.out.println("3. Year To Date");
        System.out.println("4. Previous Year");
        System.out.println("5. Search by Vendor");
        System.out.println("6. Custom Report");
        System.out.println("0. Back");
        // Prompt the user to enter a choice from the above menu.
        System.out.print("Select an option: ");
    }

    // Handles the reports page logic/flow
    public void handleReportsMenu() {
        boolean runAgain = true;
        while (runAgain) {
            showAppTitle(TOTAL_WIDTH);
            showReportsMenu();
            String reportsMenuChoice = sc.nextLine().trim();
            if (isInteger(reportsMenuChoice)) {
                switch (reportsMenuChoice.toUpperCase()) {
                    case "0":
                        runAgain = false;
                        break;
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                        account.getReport(reportsMenuChoice, TOTAL_WIDTH);
                        break;
                    case "5":
                        System.out.print("Please enter a vendor name:");
                        String vendor = sc.nextLine().trim();
                        account.getReport(vendor, TOTAL_WIDTH);
                        break;
                    case "6":
                        System.out.print("Enter start date (yyyy-MM-dd) or leave blank: ");
                        String startDateInput = sc.nextLine().trim();
                        System.out.print("Enter end date (yyyy-MM-dd) or leave blank: ");
                        String endDateInput = sc.nextLine().trim();
                        System.out.print("Enter description or leave blank: ");
                        String descriptionInput = sc.nextLine().trim();
                        System.out.print("Enter vendor or leave blank: ");
                        String vendorInput = sc.nextLine().trim();
                        System.out.print("Enter amount or leave blank: ");
                        String amountInput = sc.nextLine().trim();
                        account.getCustomReport(startDateInput, endDateInput, descriptionInput, vendorInput, amountInput, TOTAL_WIDTH);
                        break;
                    default:
                        System.out.println("Please enter a number from the menu.");
                        break;
                }
            }
            else {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public void showAppTitle(int totalWidth) {
        System.out.println("\u001B[36m" + "=".repeat(totalWidth));
        printCenteredTitle("✨\uD83C\uDF0C✨  GALAXY TRANSACTION LEDGER  ✨\uD83D\uDE80✨", totalWidth, " ");
        System.out.println("=".repeat(totalWidth) + "\u001B[0m");
    }

    // Method to generate random descriptions and vendors for the deposit transactions.
    public String[] getRandomDepositInfo() {
        String[] defaultDescriptions = {
                "Deposit - Refueling Contract",
                "Deposit - Delivery Refund",
                "Deposit - Merchant Account Sync",
                "Deposit - Fleet Rebate",
                "Deposit - Galactic Express Payment",
                "Deposit - Station Bonus Payment",
                "Deposit - Cargo Delivery Refund",
                "Deposit - Interstellar Trade Credit",
                "Deposit - Rebate Credit",
                "Deposit - Cosmic Trade Settlement"
        };
        String[] defaultVendors = {
                "Orion Fleet Co.",
                "Quantum Traders",
                "Celestial Systems",
                "Nova Credit Union",
                "Galactic Express",
                "Cosmic Trade Bank",
                "Lunar Freight",
                "Crimson Convoy",
                "Nova Credit Union",
                "Cosmic Trade Bank"
        };

        // Generate a random number
        Random rand = new Random();
        int description = rand.nextInt(defaultDescriptions.length);
        int vendor = rand.nextInt(defaultVendors.length);

        return new String[]{defaultDescriptions[description], defaultVendors[vendor]};
    }

    // Check if the passed string is an integer.
    public boolean isInteger(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        } // Done
    }

    // Check if the passed string is a double.
    public boolean isDouble(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        } // Done
    }
}
