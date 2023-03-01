# import csv
# import random
# from datetime import datetime, timedelta

# # Set the start date for the sales data
# start_date = datetime.strptime('2023-01-01', '%Y-%m-%d')

# # Create a CSV file and write the headers
# with open('orders.csv', 'a', newline='') as file:
#     writer = csv.writer(file)
#     writer.writerow(['weekNum', 'startDate', 'endDate', 'saleSubTotal', 'saleTax', 'totalSales'])

#     # Generate 52 weeks of sales data
#     for i in range(10):
#         week_num = i + 1
#         end_date = start_date + timedelta(days=6)
#         sale_subtotal = round(random.uniform(19000, 20000), 2)
#         sale_tax = round(sale_subtotal * 0.06, 2)
#         total_sales = round(sale_subtotal + sale_tax, 2)
#         writer.writerow([week_num, start_date.strftime('%Y-%m-%d'), end_date.strftime('%Y-%m-%d'), sale_subtotal, sale_tax, total_sales])

#         # Update the start date for the next week
#         start_date = end_date + timedelta(days=1)
        
# print("Data has been written to sales_data.csv file.")


import csv
import random
from datetime import datetime, timedelta

# Set the start date for the sales data
start_date = datetime.strptime('2023-01-01', '%Y-%m-%d')

# Create a CSV file and write the headers
with open('orders.csv', 'a', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['weekNum', 'startDate', 'endDate', 'saleSubTotal', 'saleTax', 'totalSales'])

    # Generate 52 weeks of sales data
    for i in range(10):
        week_num = i + 1
        end_date = start_date + timedelta(days=6)
        sale_subtotal = round(random.uniform(16000, 22000), 2)
        sale_tax = round(sale_subtotal * 0.0625, 2)
        total_sales = round(sale_subtotal + sale_tax, 2)
        writer.writerow([week_num, start_date.strftime('%Y-%m-%d'), end_date.strftime('%Y-%m-%d'), sale_subtotal, sale_tax, total_sales])

        # Update the start date for the next week
        start_date = end_date + timedelta(days=1)
        
print("Data has been written to sales_data.csv file.")