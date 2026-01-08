# Personal Finance Tracker (CLI)
A persistent Command Line Interface (CLI) application built in **Java** that allows users to log daily expenses and view their transaction history.
The key feature of this application is **Data Persistence**. Unlike basic console programs that reset when closed, this tool utilizes Java's `java.io` library to automatically save all transactions to a local text file (`expenses.txt`). This ensures data is retrieved and available every time the user restarts the program.

## Tech Stack:
Language: Java
Core Concepts: ArrayLists, Loops, Exception Handling
Data Storage: Custom File I/O (`PrintWriter` & `FileWriter`)

##  Key Features
Add Expenses: Users can input descriptions, amounts, and categories via the console.
Auto Save: Implemented a `saveData()` function that triggers immediately after every entry, ensuring zero data loss.
Persistent History: Expenses are stored in a CSV-style format in `expenses.txt`.
