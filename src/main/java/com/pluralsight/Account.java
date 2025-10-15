package com.pluralsight;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Account {
    // *** Props ***
    private double balance;
    private List<Transaction> transactions;

    // *** Const ***
    public Account() {
        this.balance = 0.0;
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
        transactions.add(newTransaction);
        // Update balance
        this.balance += newTransaction.getAmount();
    }

    // Method to get a report on the transactions.
    boolean getReport(String filter) {
        int count = 0;
        // Get and set the current day and month as well as pre previous month.
        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        YearMonth previousMonth = currentMonth.minusMonths(1);
        // Loop through the entire list of transactions
        for(Transaction t : transactions) {
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
        // If nothing printed/no transactions matched the filter provided - false.
        // Otherwise - true.
        return count != 0;
    }
}
