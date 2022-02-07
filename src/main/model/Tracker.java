package model;

import com.sun.codemodel.internal.JForEach;

import java.util.ArrayList;
import java.util.HashSet;

public class Tracker {
    private ArrayList<Expense> expenses;
    private ArrayList<Category> categories;
    private HashSet<String> categoryNames;
    private double totalSpent;
    private double totalBudget;
    private double budgetNotifcation;
    private double amountLeftInBudget;
    private double amountOverBudget;
    private Category category;

    // EFFECTS: creates a new Tracker with empty collctions of expenses, categories
    // and categoryNames
    public Tracker() {
        expenses = new ArrayList<>();
        categories = new ArrayList<>();
        categoryNames = new HashSet<>();
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets total budget and changes amountLeft to be budget - spent
    public void setTotalBudget(double amount) {
        totalBudget = amount;
        amountLeftInBudget = totalBudget - totalSpent;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: set the amount of $ the user will be notified at when they have that amount left
    // in their budget
    public void setBudgetNotifcation(double amount) {
        budgetNotifcation = amount;
    }

    // REQUIRES: Category exists, item string length is non-zero, amount >= 0
    // MODIFIES: this
    // EFFECTS: adds an expense to expenses and updates totalSpent as well as amountLeftInBudget
    // if the expense causes the user to reach near or over budget, they will be notified
    public void addExpense(String categoryName, String item, double amount) {
        Expense expense = new Expense(categoryName, item, amount);
        if (categoryNames.contains(categoryName)) {
            category = findCategory(categoryName);
            expenses.add(expense);
            totalSpent = totalSpent + amount;
            amountLeftInBudget = totalBudget - totalSpent;
            notifyNearBudget();
            notifyOverBudget();
            category.expenseInCategory(amount);
            category.notifyNearCategoryBudget(category);
            category.notifyOverCategoryBudget(category);
        } else {
            System.out.println("The Category for this expense does not exist");
        }
    }

    // REQUIRES: name length is non-zero, amount >= 0
    // MODIFIES: this
    // EFFECTS: Creates a new category if it doesn't already exist
    public void newCategory(String name, double amount) {
        Category category = new Category(name, amount);
        if (categoryNames.contains(name)) {
            System.out.println("This category already exists");
        } else {
            categories.add(category);
            categoryNames.add(name);
        }
    }

    // REQUIRES: categoryName length is non-zero
    // MODIFIES: this
    // EFFECTS: Removes category from list of categories if it exists and is not being used
    public void removeCategory(String categoryName) {
        if (doesCategoryExist(categoryName)) {
            if (isCategoryUsed(categoryName)) {
                System.out.println("This category is being used and cannot be removed.");
            } else {
                categories.remove(findCategory(categoryName));
                categoryNames.remove(categoryName);
            }
        } else {
            System.out.println("This category does not exist.");
        }
    }

    // REQUIRES: categoryName length is non-zero
    // EFFECTS: Shows expenses for a certain category
    public void showExpensesForCategory(String categoryName) {
        if (doesCategoryExist(categoryName)) {
            category = findCategory(categoryName);
            for (Expense expense : expenses) {
                if (expense.getCategoryName().equals(categoryName)) {
                    System.out.println("You bought " + expense.getItemName()
                            + " for " + expense.getMoneySpent() + " in the "
                            + expense.getCategoryName() + " category.");
                }
            }
        } else {
            System.out.println("This category does not exist.");
        }
    }


    // REQUIRES: categoryName length is non-zero
    public boolean doesCategoryExist(String categoryName) {
        if (categoryNames.contains(categoryName)) {
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: name length is non,zero
    // EFFECTS: Returns category in list of categories given it's name
    public Category findCategory(String name) {
        for (Category category : categories) {
            if (category.getCategoryName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    // REQUIRES: name length is non-zero
    // EFFECTS: Returns true if an expense has a category with the given category name
    // otherwise false
    public boolean isCategoryUsed(String name) {
        for (Expense expense : expenses) {
            if (expense.getCategoryName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Notifies user when amount left in budget is less than notification level but more than 0
    public boolean notifyNearBudget() {
        if (amountLeftInBudget <= budgetNotifcation) {
            if (amountLeftInBudget < 0) {
                return false;
            }
            System.out.println("Warning: You only have $" + amountLeftInBudget + " left in your budget");
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Notifies user when their expenses exceed budget
    public boolean notifyOverBudget() {
        if (totalSpent > totalBudget) {
            amountOverBudget = totalSpent - totalBudget;
            System.out.println("You are over budget by $" + amountOverBudget);
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Shows user all expenses
    public void showAllExpenses() {
        for (Expense expense : expenses) {
            System.out.println("You bought " + expense.getItemName() + " for " + expense.getMoneySpent() + " in the "
                    + expense.getCategoryName() + " category.");
        }
    }

    // EFFECTS: Shows user all categories they have created
    public void showAllCategories() {
        System.out.println("Categories: ");
        for (Category category : categories) {
            System.out.println(category.getCategoryName());
        }
    }

    // EFFECTS: Shows user how much they have spent in total
    public void showTotalSpent() {
        System.out.println("You have spent $" + totalSpent + " in total.");
    }

    // EFFECTS: Shows user their current set budget
    public void showTotalBudget() {
        System.out.println("Your budget is $" + totalBudget + ".");
    }

    // EFFECTS: Shows user their current set budget notification
    public void showBudgetNotifcation() {
        System.out.println("You will get notifications when you have $" + budgetNotifcation
                + " left in your budget.");
    }

    public double getBudgetNotification() {
        return budgetNotifcation;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getAmountLeftInBudget() {
        return amountLeftInBudget;
    }

    public double getAmountOverBudget() {
        return amountOverBudget;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public HashSet<String> getCategoryNames() {
        return categoryNames;
    }

}
