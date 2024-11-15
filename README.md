# Simple-Bank-Management-System-in-Java
Task
Develop a Java application that simulates a Simple Banking System. Design 3NF DB design and class 
diagram for this system
1. Banking Operations:
 Users can create an account (with account number, name, and initial balance).
 Users can perform transactions like deposit and withdrawal.
 Users can view transaction history, which will be logged to a file.
2. Error and Exception Handling:
 When a user tries to withdraw more money than available, the InsufficientFundsException 
will be thrown.
 If an invalid account number is entered, the InvalidAccountNumberException will be thrown.
 Any file or database-related issues will be caught and handled using checked exceptions.
 Any unexpected issues (like trying to operate on null account objects or illegal inputs) will be 
caught using unchecked exceptions.
3. Logs:
 Each transaction will be logged in a file (transactions.log), and errors (such as invalid 
transactions) will also be logged in a separate error log (error.log).
4. Graceful Shutdown:
 Regardless of exceptions, the system should gracefully close all open resources (e.g., file 
streams, database connections).
