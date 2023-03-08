import csv
import random
from datetime import datetime, timedelta

# Set the start date for the sales data
start_date = datetime.strptime('2023-01-01', '%Y-%m-%d')

# Create a CSV file and write the headers
with open('newOrders.csv', 'a', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['dayID', 'date', 'saleSubTotal', 'saleTax', 'totalSales'])

    # Generate sales data for every day of the year
    for i in range(70):
        date = start_date + timedelta(days=i)
        sale_subtotal = round(random.uniform(2239.73, 3239.73), 2)
        sale_tax = round(sale_subtotal * 0.0625, 2)
        total_sales = round(sale_subtotal + sale_tax, 2)
        day_id = 365+ i + 1  # Calculate the dayID as i + 1
        writer.writerow([day_id, date.strftime('%Y-%m-%d'), sale_subtotal, sale_tax, total_sales])
        
print("Data has been written to newOrders.csv file.")
