package model;

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

    // EFFECTS: creates a new Tracker with empty collections of expenses, categories
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
        }
    }

    // REQUIRES: name length is non-zero, amount >= 0
    // MODIFIES: this
    // EFFECTS: Creates a new category if it doesn't already exist
    public void newCategory(String name, double amount) {
        Category category = new Category(name, amount);
        if (!doesCategoryExist(name)) {
            categories.add(category);
            categoryNames.add(name);
        }
    }

    // REQUIRES: categoryName length is non-zero
    // MODIFIES: this
    // EFFECTS: Removes category from list of categories if it exists and is not being used
    public void removeCategory(String categoryName) {
        if (doesCategoryExist(categoryName)) {
            if (!isCategoryUsed(categoryName)) {
                categories.remove(findCategory(categoryName));
                categoryNames.remove(categoryName);
            }
        }
    }

    // REQUIRES: name length is non-zero and amount >= 0
    // EFFECTS: Sets a specific category's budget
    public void setCategoryBudget(String name, double amount) {
        category = findCategory(name);
        category.setBudget(amount);
    }

    // REQUIRES: name length is non-zero and amount >= 0
    // EFFECTS: Sets a specific category's budget notification
    public void setCategoryBudgetNotification(String name, double amount) {
        category = findCategory(name);
        category.setCategoryBudgetNotifcation(amount);
    }


    // REQUIRES: categoryName length is non-zero
    // EFFECTS: returns true or false depending on if the category exists
    public boolean doesCategoryExist(String categoryName) {
        if (categoryNames.contains(categoryName)) {
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: name length is non-zero
    // EFFECTS: Returns category in list of categories given its name
    public Category findCategory(String name) {
        if (doesCategoryExist(name)) {
            for (Category category : categories) {
                if (category.getCategoryName().equals(name)) {
                    return category;
                }
            }
            return null;
        }
        return null;
    }

    // REQUIRES: name length is non-zero
    // EFFECTS: Returns true if an expense has a category with the given category name
    // otherwise false
    public boolean isCategoryUsed(String name) {
        if (doesCategoryExist(name)) {
            for (Expense expense : expenses) {
                if (expense.getCategoryName().equals(name)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    // REQUIRES: original and changed length is non-zero
    // MODIFIES: this
    // EFFECTS: changes original category name to changed
    public void changeCategoryName(String original, String changed) {
        if (doesCategoryExist(original)) {
            category = findCategory(original);
            category.changeCategoryName(changed);
            categoryNames.remove(original);
            categoryNames.add(changed);
        }
    }

    // EFFECTS: Notifies user when amount left in budget is less than notification level but more than 0
    public boolean notifyNearBudget() {
        if (amountLeftInBudget <= budgetNotifcation) {
            if (amountLeftInBudget < 0) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Notifies user when their expenses exceed budget
    public boolean notifyOverBudget() {
        if (totalSpent > totalBudget) {
            amountOverBudget = totalSpent - totalBudget;
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: returns amount left in budget in the category of name
    public double showCategoryAmountLeftInBudget(String name) {
        Category category = findCategory(name);
        double amount = category.getCategoryAmountLeftInBudget();
        return amount;

    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: returns budget of category matching input
    public double showCategoryBudget(String name) {
        Category category = findCategory(name);
        double budget = category.getCategoryBudget();
        return budget;
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: returns budget notification of category matching input
    public double showCategoryBudgetNotifcation(String name) {
        Category category = findCategory(name);
        double notifcation = category.getCategoryBudgetNotification();
        return notifcation;
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
