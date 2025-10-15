# Capstone 1 Planning Outline

#### 

#### Classes:

* Main
* User
* Account
* Transaction
* Controller
* FileManager





#### Class Responsibilities:

###### Main:

* Initialize the program and act as an entry point to the program

###### Account:

* Contains a balance which can be lowered or raised depending on which transactions take place
* Store a list of the transactions
* Can perform credit and debit transactions to add or take from the balance
* Can return the balance and/or the list of transactions

###### Transaction:

* Contains data about each transaction(e.g. id, amount, company, date)
* Can return the data about each of the above properties

###### Controller:

* Contains an account object which can be modified
* Menu screens
* Methods to check for int and double

###### FileManager:

* Pull in previously recorded transactions from a file
* Write new transactions to the file

#### 

#### Class Contents(properties/methods):

###### Main:

* Controller object property and a call to the start method

###### Account:

* P-transactionHistory ArrayList - new ArrayList in const
* P-balance - default 0
* M-boolean AddTransaction(Transaction) Adds a new transaction to transactionHistory. Also contains an update to balance(unless suggested to put in a method) return true when the transaction goes through, otherwise false
* M-Boolean GetReport(String filter) - 1 loop, multiple filters controlled by ifs or maybe switch (NO INPUT RETREIVED HERE, only received via method parameter(i.e. "filter")) \[filter keys/cases: A-All, D-Deposits, P-Payments, 1-Month To Date, 2-Previous To Month, 3-Year To Date, 4-Previous Year, Other Strings-Vendor search(use .contains(filter))] return true when something is printed/found, otherwise false
* M-GetBalance()

###### Transaction:

* Date
* Time
* Description
* Vendor
* Amount (regular for pos-credit, negative for neg-debit)

###### Controller:

* Account account
* Start()
*  	Main menu loop (e.g. Add Deposit, Make Payment, Ledger, or Exit)
*  		gather user input (validate)
*  		switch(input)
*  			case addDeposit
*  			case makePayment
*  			case ledger
*  			case exit
*  			default
* AddDeposit()
*  	Add deposit loop
*  		prompt enter amount
*  		gather user input (validate)
*  		create new transaction deposit
*  		account.addTransaction(deposit)
*  		(not sure if I need to prompt for any other info or just set defaults)
* makePayment()
*  	make payment loop
*  		prompt enter amount
*  		gather user input (validate)
*  		prompt enter vendor/recipient
*  		gather user input (validate if need be)
*  		prompt enter description (validate)
*  		gather user input (validate if need be)
*  		create new transaction payment
*  		account.addTransaction(payment)
* ledgerMenu()
*  	boolean runAgain = true
*  	ledger menu loop(runAgain) (e.g. A-All transactions, D-deposits, P-payments, R-reports, H-home)
*  		prompt for user input
*  		gather user input (validate- maybe)
*  		switch(input)
*  			case A
*  			case D
*  			case P
*  				if(getReport(input)) //
*  					// good, continue, maybe break back to the ledger menu loop
*  				else
*  					if input.equalsIgnoreCase(A)
*  						// Transactions history is empty
*  						// break back to the ledger menu loop
*  					else
*  						if input.equalsIgnoreCase(D)
*  							// No deposits
*  							// break back to the ledger menu loop
*  						else
*  							// No payments
*  							// break back to the ledger menu loop
*  			case R
*  				loop for filter
*  					prompt for filtering option 'filter' (validate)
*  					if (filter.equals(0))
*  						break; // break back to the ledger menu loop
*  					else if (!(filter.equals(1)||filter.equals(2)||filter.equals(3)||filter.equals(4)||filter.equals(5)))
*  						message: please enter an option form the menu (1, 2, 3, 4, or 5)
*  						// continue
*  					else
*  						getReport(filter) (reports/filtering logic handled in Account)
*  						// break back to the ledger menu loop
*  			case H
*  				runAgain = false
*  				break

###### FileManager:

* transactionHistory ArrayList =
* readTransactionHistoryFromFile()
*  	read from a transactions file with example/existing transactions
* List<Transaction> getTransactionHistoryFromFile() - returns the FileManager transactionHistory ArrayList
* writeUpdatedTransactionHistoryToFile() - writes the updated list, with new transactions, to the transactions file.
