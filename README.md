# Java GUI Application for Inventory and Sales Management

This Java-based GUI application is designed for managing inventory, sales, and employees in a business environment. It offers various features such as cart management, SCRUM functionality for inventory and sales, and analytics to view reports.

## Folder Structure

The workspace is organized into the following folders:

- src: Contains the source files
- lib: Contains the dependencies
- The compiled output files will be generated in the bin folder by default.

To customize the folder structure, open .vscode/settings.json and update the related settings.

## Running the Code

To compile and run the code with the database, follow these steps:

- Compile the Java files:
- Copy code
- javac *.java
- java -cp ".:postgresql-42.2.8.jar" App GUI Login MainPanel NavPanel ManagerPanel Sales
- Manager View Login Password is 1172
- Server View Login Password is 5365

## Features of the Program

- Cart function that updates with selected inventory
- Manager button that allows SCRUM functionality in Inventory, Menu, Total Sales, Daily Sales, and Employees
- Sales Bridge button that allows for server to refund, delete, or add any orders
- Compile button adds the orders to DailySales Table, Sales Table, X Report table
- Analytics creates a view of a Sales Report, X and Z report, Excess Report, and a Restock Report
- Also a logout and login function in which different manager and server views are shown

## How to Log in to the Database

- Username: psql -h csce-315-db.engr.tamu.edu -U csce315331_epsilon_master -d csce315331_epsilon
- Password: *******
