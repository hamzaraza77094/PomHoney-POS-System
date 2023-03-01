#Python Script to fill the CSV so I dont have 2
from random import randint, choice

from numpy import number

global entree, extras, drink
entree = {
        'gyro': 8.09,
        'bowl': 8.09
    }
extras = {
    'hummas & peta': 3.49,
    '2 Falafel': 3.49,
    'Extra Protein': 2.49,
    'Extra Dressing': 0.39
}
drink = {
    'Fountain Drink': 2.45
}


def randomMeal():
    
    categories = [entree, drink, extras]
    number_of_categories = randint(1,3)  
    number_of_items = randint(1,7)
    
    # specific_items_ordered = []
    
    subTotal = 0
    saleContents = ""
    
    for i in range(len(number_of_items)):
        item_dict = categories[choice(range(0,number_of_categories))]
        if item_dict == 1:
            item_in_entree = choice([0,1])
            
            item_key_list = item_dict.keys()
            item_value_list = item_dict.values()
            
            subTotal += item_value_list[item_in_entree]
            saleContents += f'{item_key_list[item_in_entree]} & '
            
        elif item_dict == 2:
            item_in_drink = 0
            
            item_key_list = item_dict.keys()
            item_value_list = item_dict.values()
            
            subTotal += item_value_list[item_in_drink]
            saleContents += f'{item_key_list[item_in_drink]} & '
 
        elif item_dict == 3:
            item_in_extra = choice([0,1,2,3])
            
            item_key_list = item_dict.keys()
            item_value_list = item_dict.values()
            
            subTotal += item_value_list[item_in_extra]
            saleContents += f'{item_key_list[item_in_extra]} &'
        
        else:
            print("Shit")
            
        salesTax = subTotal * 0.0625
        
        return subTotal, salesTax, saleContents
  
with open('./q1.csv', 'w') as outCSV:
    outCSV.write('salesID, dateOfSale, saleSubTotal, saleTax, saleContents')
    for i in range(1, 90):
        randoSubTotal, randoSalesTax, randoSalesContent = randomMeal()
        outCSV.write(f'{i}, {}/{}/2023, {randoSubTotal}, {randoSalesTax}, {randoSalesContent}')