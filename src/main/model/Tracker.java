package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            totalSpent = totalBudget + amount;
            amountLeftInBudget = totalBudget - totalSpent;
            notifyNearBudget();
            notifyOverBudget();
            category.expenseInCategory(amount);
            category.notifyOverCategoryBudget(category);
            category.notifyOverCategoryBudget(category);
        } else {
            System.out.println("The Category for this expense does not exist");
        }
    }

    public Category findCategory(String name) {
        for (Category category : categories) {
            if (category.getCategoryName() == name) {
                return category;
            }
        }
    }

    // REQUIRES: item string length is non-zero, amount >= 0
    // MODIFIES: this
    // EFFECTS: Removes an already existing Expense from expenses
    /* public void removeExpense(String category, String item, double amount) {
        if (expenses.contains(expense)) {
            expenses.remove(expense);
            totalSpent = totalSpent - amount;
            amountLeftInBudget = amountOverBudget + amount;
        } else {
            System.out.println("This expense does not exist");
        }
    }
*/
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

 /*   public void removeCategory(String name, double amount) {
        Category category = new Category(name, amount);
        if (categories.contains(category)) {
            for (Expense expense : expenses) {
                // for every expense, check if category is used, if so then cannot remove category, but if not then
                // remove}
        } else {
            System.out.println("This category does not exist");
        }
    }}

    public boolean isCategoryUsed(Expense expense, Category category) {
        if (expense.getCategory() == category) {
            return true;
        } else {
            return false;
        }
    }*/

    // EFFECTS: Notifies user when amount left in budget is less than notification level but more than 0
    public boolean notifyNearBudget() {
        if (amountLeftInBudget < budgetNotifcation) {
            if (amountLeftInBudget < 0) {
                return false;
            }
            System.out.println("Warning: You only have " + amountLeftInBudget + " left in your budget");
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
            System.out.println("You bought" + expense.getItemName() + " for " + expense.getMoneySpent() + "in the "
                    + expense.getCategory() + "category.");
        }
    }

    // EFFECTS: Shows user all categories they have created
    public void showAllCategories() {
        System.out.println("Categories: ");
        for (Category category : categories) {
            System.out.println(category.getCategoryName());
        }
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
