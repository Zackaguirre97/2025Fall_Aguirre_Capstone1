package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void testCalculateBalance() {

        Account account = new Account();
        account.addTransaction(new Transaction(LocalDate.now(), LocalTime.now(), "Deposit", "Test Vendor", 1000.00));
        account.addTransaction(new Transaction(LocalDate.now(), LocalTime.now(), "Payment", "Test Vendor", -400.00));

        account.calculateBalance();
        Assertions.assertEquals(600.00, account.getBalance(), 0.001);
    }

    @Test
    void testFileWriteAndRead() {
        FileManager fileManager = new FileManager();
        List<Transaction> testList = new ArrayList<>();
        testList.add(new Transaction(LocalDate.now(), LocalTime.now(), "Deposit Test", "Vendor X", 500.00));

        fileManager.updateTransactionsFile(testList);

        fileManager.loadTransactionsFromFile();
        Assertions.assertFalse(fileManager.getTransactionsFromFile().isEmpty());
    }

    @Test
    void testCustomReportRuns() {
        Account account = new Account();
        account.addTransaction(new Transaction(LocalDate.now(), LocalTime.now(), "Deposit", "Alpha Bank", 200.00));
        account.addTransaction(new Transaction(LocalDate.now(), LocalTime.now(), "Payment", "Beta Bank", -100.00));

        // Call the method to ensure it executes without exceptions
        account.getCustomReport(
                LocalDate.now().minusDays(1).toString(),
                LocalDate.now().plusDays(1).toString(),
                "",
                "Alpha Bank",
                "",
                105
        );

        // If it reaches here, the test “passes” — no exceptions thrown
    }
}