package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Handles reading from & writing to files.
public class FileManager {
    // *** Props ***
    private static final String FILE_PATH = "src/main/resources/transactions.csv"; // Links to the file containing past transaction's data.
    private List<Transaction> transactionsFromFile = new ArrayList<>(); // List of Transaction objects

    // *** Const ***
    public FileManager() {
        loadTransactionsFromFile();
    }

    // *** Getter ***
    public List<Transaction> getTransactionsFromFile() {
        return transactionsFromFile;
    }

    // *** Methods ***
    // Method to load a list of transactions from the contents of a file.
    public void loadTransactionsFromFile() {
        // Try to open the file.
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            // Method variable
            String line;
            // While there is a line to read in the file.
            while ((line = reader.readLine()) != null) {
                // Create a string array to hold the different part of the file. Separate the lines by "|".
                String[] parts = line.split("\\|");
                // If all data items are present in the line.
                if (parts.length >= 4) {
                    // Assign the different parts of the line to temp variables.
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    // Pass the temp variables to the Transaction constructor to create a new transaction.
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    // Add the transaction to the class list of transactions
                    transactionsFromFile.add(transaction);
                }
            }
            // Close the reader
            reader.close();
        } // Catch the potential errors/exceptions.
        catch (FileNotFoundException ex) {
            System.out.println("Error: File not found!");
        }
        catch (IOException e) {
            System.out.println("Error: IO Exception");
        }
        catch (Exception e) {
            System.out.println("Error: File reader error");
        }
    }

    // Method to write(overwrite) the transactions file with the updated list of transactions.
    public void updateTransactionsFile(List<Transaction> updatedList) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction t : updatedList) {
                writer.write(String.format(
                        "%s|%s|%s|%s|%.2f\n",
                        t.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        t.getTransactionTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                ));
            }
            // Close the writer
            writer.close();
        }
        catch(IOException e) {
            System.out.println("Error: IOException");
        }
        catch(Exception e) {
            System.out.println("Error: File Writer error");
        }
    }
}
