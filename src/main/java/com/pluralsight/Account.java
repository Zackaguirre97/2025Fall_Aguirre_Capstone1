package com.pluralsight;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account {
    // *** Props ***
    private double balance;
    private List<Transaction> transactions;

    // *** Const ***
    public Account() {
        this.balance = getBalance();
        this.transactions = new ArrayList<>();
    }

    // *** Getters/Setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // *** Methods ***
    // Method to add a new transaction to the list of all transactions.
    public void addTransaction(Transaction newTransaction) {
        // Add the transaction to the list.
        transactions.add(0, newTransaction);
    }

    // Method to calculate the balance.
    public void calculateBalance() {
        for (Transaction t : this.transactions) {
            this.balance += t.getAmount();
        }
    }

    // Method to get a report on the transactions.
    boolean getReport(String filter) {
        int count = 0;
        // Determine title based on filter
        String title;
        switch (filter.toUpperCase()) {
            case "A":
                title = "📜 All Transactions";
                break;
            case "D":
                title = "💰 Deposits Only";
                break;
            case "P":
                title = "💳 Payments Only";
                break;
            case "1":
                title = "🗓 Month-to-Date Transactions";
                break;
            case "2":
                title = "📅 Previous Month Transactions";
                break;
            case "3":
                title = "📈 Year-to-Date Transactions";
                break;
            case "4":
                title = "🕰 Previous Year Transactions";
                break;
            default:
                title = "🔍 Transactions for Vendor: " + filter;
        }

        // Print title header
        int totalWidth = 107;
        printCenteredTitle(title, totalWidth);
        // Column headers
        System.out.printf("%-14s %-10s %-42s %-25s %12s%n",
                "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");
        System.out.println("-".repeat(totalWidth));


        // Get and set the current day and month as well as pre previous month.
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        YearMonth previousMonth = currentMonth.minusMonths(1);
        // Sort the list of transactions.
        this.transactions.sort(Comparator
                .comparing(Transaction::getTransactionDate)
                .thenComparing(Transaction::getTransactionTime)
                .reversed());

        // Loop through the entire list of transactions
        for(Transaction t : this.transactions) {
            // Get the date of the transaction.
            LocalDate txDate = t.getTransactionDate();
            // Variable/trigger for deciding whether a transaction should be displayed.
            boolean match = false;
            // Determine whether the current transaction is a match for the filter option selected.
            switch(filter.toUpperCase()) {
                case "A": // ALL
                    match = true;
                    break;
                case "D": // Deposits
                    match = t.getAmount() > 0;
                    break;
                case "P": // Payments
                    match = t.getAmount() < 0;
                    break;
                case "1": // Month To Date
                    match = YearMonth.from(txDate).equals(currentMonth);
                    break;
                case "2": // Previous Month
                    match = YearMonth.from(txDate).equals(previousMonth);
                    break;
                case "3": // Year To Date
                    match = txDate.getYear() == today.getYear();
                    break;
                case "4": // Previous year
                    match = txDate.getYear() == today.getYear() - 1;
                    break;
                default: // Vendor search
                    match = t.getVendor().toLowerCase().contains(filter.toLowerCase());
            }

            // Display all
            if (match) {
                t.getTransactionData();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No transactions found for this filter.");
            return false;
        }

        System.out.println("------------------------------------");
        System.out.printf("Current Balance: $%.2f%n", this.balance);
        System.out.println("------------------------------------");

        return true;
    }

    // Method to display a centered title.
    public static void printCenteredTitle(String title, int totalWidth) {
        int dashCount = (totalWidth - title.length() - 2) / 2;
        if (dashCount < 0) dashCount = 0;
        String dashes = "-".repeat(dashCount);
        System.out.println(dashes + " " + title + " " + dashes);
    }

}
