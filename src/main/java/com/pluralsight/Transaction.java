package com.pluralsight;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    // *** Props ***
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private String description;
    private String vendor;
    private double amount;

    // *** Const ***
    // All args
    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String description, String vendor, double amount) {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    // No Date or Time args
    public Transaction(String description, String vendor, double amount) {
        this.transactionDate = LocalDate.now();
        this.transactionTime = LocalTime.now();
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // *** Getters/Setters
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // *** Methods ***
    public void getTransactionData() {
        System.out.printf("%s|%s|%s|%s|%.2f%n",
                this.transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                this.transactionTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                this.description,
                this.vendor,
                this.amount
        );
    }
}
