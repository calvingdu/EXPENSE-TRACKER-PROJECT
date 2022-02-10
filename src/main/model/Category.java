package model;

public class Category {
    private double budget;
    private double amountSpent;
    private String categoryName;
    private double categoryBudgetNotifcation;
    private double categoryAmountLeftInBudget;
    private double categoryAmountOverBudget;

    // REQUIRES: name has non-zero length
    // EFFECTS: creates category with a name and a budget
    public Category(String name, double amount) {
        categoryName = name;
        budget = amount;
        categoryAmountLeftInBudget = amount;
        amountSpent = 0;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Changes budget to amount
    public void setBudget(double amount) {
        categoryAmountLeftInBudget = categoryAmountLeftInBudget + (amount - budget);
        budget = amount;
    }

    // REQUIRES: name has non-zero length
    // MODIFIES: this
    // EFFECTS: Changes Category name to inputted name
    public void changeCategoryName(String name) {
        categoryName = name;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Changes budgetNotifcation to amount
    public void setCategoryBudgetNotifcation(double amount) {
        categoryBudgetNotifcation = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Adds an expense amount to the total amount spent in the category
    public void expenseInCategory(double amount) {
        amountSpent = amountSpent + amount;
        categoryAmountLeftInBudget = budget - amountSpent;
        categoryAmountOverBudget = amountSpent - budget;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Notifies user if amountSpent < budgetCutoff;
    public boolean notifyNearCategoryBudget(Category category) {
        if (categoryAmountLeftInBudget <= categoryBudgetNotifcation) {
            if (categoryAmountLeftInBudget < 0) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Notifies user if amountSpent > budget
    public boolean notifyOverCategoryBudget(Category category) {
        if (amountSpent > budget) {
            categoryAmountOverBudget = amountSpent - budget;
            return true;
        } else {
            return false;
        }
    }

    public double getCategoryAmountSpent() {
        return amountSpent;
    }

    public double getCategoryBudget() {
        return budget;
    }

    public double getCategoryBudgetNotification() {
        return categoryBudgetNotifcation;
    }

    public double getCategoryAmountLeftInBudget() {
        return categoryAmountLeftInBudget;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getCategoryAmountOverBudget() {
        return categoryAmountOverBudget;
    }
}
