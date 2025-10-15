# 💼 Capstone 1 – Project Planning Outline

## 🧱 Classes Overview
| Class | Description |
|-------|--------------|
| **Main** | Entry point of the program. Initializes and starts the application. |
| **User** | (Optional) Represents an individual using the system. |
| **Account** | Manages account balance and transaction history. Performs credit/debit operations. |
| **Transaction** | Represents individual transactions (deposit/payment) with related details. |
| **Controller** | Handles user interaction, menus, and overall application flow. |
| **FileManager** | Manages reading and writing transaction data from/to files. |

---

## 🧩 Class Responsibilities

### **Main**
- Initializes the program and acts as the entry point.
- Creates and runs the `Controller` object.

---

### **Account**
- Stores:
    - A **balance** (which increases or decreases based on transactions).
    - A **list of transactions**.
- Responsibilities:
    - Perform **credit (deposit)** and **debit (payment)** operations.
    - Return balance and transaction list.
    - Generate reports with filtering options.

---

### **Transaction**
- Holds all data related to a single transaction:
    - `id`
    - `amount`
    - `description`
    - `vendor`
    - `date` and `time`
- Provides getters to return transaction details.

---

### **Controller**
- Holds a single `Account` object.
- Manages user menus and input validation.
- Routes actions such as:
    - Add Deposit
    - Make Payment
    - View Ledger / Reports
    - Exit

---

### **FileManager**
- Reads previously recorded transactions from a file.
- Writes updated transactions to the file.
- Provides access to transaction history data.

---

## 🧠 Class Structure Details

### **Main**
**Properties**
- `Controller controller`

**Methods**
- `main()` → Creates controller and calls `controller.start()`

---

### **Account**
**Properties**
- `ArrayList<Transaction> transactionHistory` – initialized in constructor
- `double balance` – default `0.0`

**Methods**
- `boolean addTransaction(Transaction t)`
    - Adds a transaction to the history and updates the balance.
- `boolean getReport(String filter)`
    - Generates filtered transaction reports.
    - Supported filters:
        - `A` → All
        - `D` → Deposits
        - `P` → Payments
        - `1` → Month To Date
        - `2` → Previous Month
        - `3` → Year To Date
        - `4` → Previous Year
        - Any other string → Vendor search (`.contains(filter)`)
- `double getBalance()` → Returns account balance.

---

### **Transaction**
**Properties**
- `String date`
- `String time`
- `String description`
- `String vendor`
- `double amount` *(positive = deposit, negative = payment)*

---

### **Controller**

#### Properties
- `Account account`

#### Methods
##### `start()`
- Main program loop
- Displays main menu options:
    1. Add Deposit
    2. Make Payment
    3. Ledger
    4. Exit

##### `addDeposit()`
- Prompts for:
    - Amount (validate)
- Creates and adds a new **deposit** transaction.

##### `makePayment()`
- Prompts for:
    - Amount (validate)
    - Vendor / Recipient (validate)
    - Description (validate)
- Creates and adds a new **payment** transaction.

##### `ledgerMenu()`
- Displays ledger menu options:
    - `A` → All Transactions
    - `D` → Deposits
    - `P` → Payments
    - `R` → Reports
    - `H` → Home
- Runs loop until user exits.
- Uses `getReport()` from `Account` for filtered results.

##### Example Flow (Pseudo)
```text
while (true)
    displayMainMenu()
    input = getUserChoice()
    switch (input)
        case 1: addDeposit()
        case 2: makePayment()
        case 3: ledgerMenu()
        case 4: exit()
        default: invalidOption()


---

### **FileManager**

**Properties**

* `ArrayList<Transaction> transactionHistory`

**Methods**

* `void readTransactionHistoryFromFile()`

    * Reads transactions from file and populates `transactionHistory`.

* `List<Transaction> getTransactionHistoryFromFile()`

    * Returns stored transactions.

* `void writeUpdatedTransactionHistoryToFile()`

    * Saves the updated list (including new transactions) to the file.

---

## 📄 Notes & Ideas

* Use **validation helper methods** for integers and doubles.
* Ensure `FileManager` uses try-with-resources for safe file handling.
* Consider CSV or JSON format for transaction files.
* Keep filtering logic in `Account`, not `Controller`.

---

## 🧭 Suggested Build Order

1. **Transaction**
2. **Account**
3. **FileManager**
4. **Controller**
5. **Main**

---

## 🧩 Optional Future Enhancements

* Implement `User` class (for multiple accounts).
* Add sorting (by date, amount).
* Add date-based filtering using `LocalDate` and `java.time`.
* Add summaries (e.g., total deposits/payments for period).

```

