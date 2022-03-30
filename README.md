# Budget and Expenses Tracker
This application will be used to keep track of expenses
that the user inputs in order to categorize, organize and keep track of as well 
as  notify the user when the budget is about to be surpassed.

##This application will...
- keep track of expenses in categories (such as groceries, 
eating out, bills, gifts) 
- lets the user ask how much they spent in each category
- lets the user ask how much spending they have left in their budgets 
for each category (as well as for the total)
- lets the user customize their budgets and categories
- notifies the user when the user is nearing/surpassed their budget

##Usefulness
This application will be useful to anyone looking to manage their finances
and keep track of their budget. As someone who does this themselves (though 
in a very manual way in excel), I find this idea very interesting and
the customization I would like (such as which items identify in what category) is not
something common in big banking apps that keep track of expenses. 

##User Stories
- As a user, I want to be able to add an expenditure to my history of expenditures
and categorize what type of expense this expenditure is
- As a user, I want to be able to set my budgets and the amount of dollars before my budget
that will cause me to be notified of reaching said budget
- As a user, I want to be able to be told how much money I have left in my budget, how much 
I have spent in total, and in each category
- As a user, I want to be able to customize my categories, with titles that I choose, being able to remove, add, and
change the name of
- As a user, I want to be able to be shown all of my expenses and my expenses in each category

- As a user, I want to be able to save my categories and my expenses  
- As a user, I want to be able to be able to load my previously created categories and my expenses  

## Sample Event Log
Wed Mar 30 12:45:39 PDT 2022
Set Budget: $1000.0
Wed Mar 30 12:45:41 PDT 2022
Set Notification Amount: $100.0
Wed Mar 30 12:45:44 PDT 2022
Added Category: category1
Wed Mar 30 12:45:47 PDT 2022
Added Category: category2
Wed Mar 30 12:45:52 PDT 2022
Added Expense: category1 // 100 // $100.0
Wed Mar 30 12:46:00 PDT 2022
Added Expense: category1 // 850 // $850.0
Wed Mar 30 12:46:00 PDT 2022
Notified Near Budget
Wed Mar 30 12:46:08 PDT 2022
Added Expense: category2 // 100 // $100.0
Wed Mar 30 12:46:08 PDT 2022
Notified Over Budget