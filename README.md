## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Running the Code

Compile the code and then run it with the database

- javac *.java
- java -cp ".:postgresql-42.2.8.jar" App GUI Login MainPanel NavPanel ManagerPanel Sales

## Features of the Progam:

- Cart function that updates with selected inventory
- Manager button that allows SCRUM functionality in Inventory, Menu, Total Sales, Daily Sales, and Employees
- Sales Bridge button that allows for server to refund, delete, or add any orders
  - Compile button adds the orders to DailySales Table, Sales Table, X Report table
- Analytics creates a view of a Sales Report, X and Z report, Excess Report, and a Restock Report
- Also a logout and login function in which different manager and server views are shown.

## How to log in to the database 

- Username: psql -h csce-315-db.engr.tamu.edu -U csce315331_epsilon_master -d csce315331_epsilon
- Password: *******

