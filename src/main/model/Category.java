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
    // EFFECTS: Changes Category name to name
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
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Notifies user if amountSpent < budgetCutoff;
    public boolean notifyNearCategoryBudget(Category category) {
        if (categoryAmountLeftInBudget <= categoryBudgetNotifcation) {
            if (categoryAmountLeftInBudget < 0) {
                return false;
            }
            System.out.println("Warning: You only have $" + categoryAmountLeftInBudget + " left in your "
                    + categoryName + " Category");
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
            System.out.println("You are over budget by $" + categoryAmountOverBudget + " in the "
                    + categoryName + " category");
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: Shows user how much they have spent in total in the category
    public void showCategorySpent() {
        System.out.println("You have spent $" + amountSpent + " in the " + categoryName + " category.");
    }

    // EFFECTS: Shows user their current set budget in the category
    public void showCategoryBudget() {
        System.out.println("Your budget is $" + budget + " in the " + categoryName + " category.");
    }

    // EFFECTS: Shows user their current set budget notification in the category
    public void showCategoryBudgetNotification() {
        System.out.println("You will get notifications when you have $" + categoryBudgetNotifcation
                + " left in your " + categoryName + " category budget.");
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public double getBudget() {
        return budget;
    }

    public double getCategoryAmountLeftInBudget() {
        return categoryAmountLeftInBudget;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
