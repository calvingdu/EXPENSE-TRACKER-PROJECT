package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a category (that a user will create/add) of the expense
public class Category implements Writable {
    private double budget;
    private double amountSpent;
    private String categoryName;
    private double categoryBudgetNotification;
    private double categoryAmountLeftInBudget;
    private double categoryAmountOverBudget;

    // REQUIRES: name has non-zero length, amount >= 0
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
    // EFFECTS: Changes budget notification to amount
    public void setCategoryBudgetNotification(double amount) {
        categoryBudgetNotification = amount;
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
    // EFFECTS: return true if amountSpent < budgetCutoff, otherwise false
    public boolean notifyNearCategoryBudget(Category category) {
        if (categoryAmountLeftInBudget <= categoryBudgetNotification) {
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
    // EFFECTS: return true if amountSpent > budget, otherwise false
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
        return categoryBudgetNotification;
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

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets total spent in category
    public void setCategorySpent(double amount) {
        amountSpent = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the amount of money spent over budget in category
    public void setAmountOverBudget(double amount) {
        categoryAmountOverBudget = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets amount left in budget in category
    public void setAmountLeftInBudget(double amount) {
        categoryAmountLeftInBudget = amount;
    }

    // EFFECTS: creates jsonObject of category
    public JSONObject toJson() {
        JSONObject jsonCategory = new JSONObject();
        jsonCategory.put("category", categoryName);
        jsonCategory.put("budget", budget);
        jsonCategory.put("spent", amountSpent);
        jsonCategory.put("notification", categoryBudgetNotification);
        jsonCategory.put("left", categoryAmountLeftInBudget);
        jsonCategory.put("over", categoryAmountOverBudget);
        return jsonCategory;
    }

    // EFFECTS: creates jsonObject of categoryName
    public JSONObject toJsonCategoryName() {
        JSONObject jsonCategoryName = new JSONObject();
        jsonCategoryName.put("name", categoryName);
        return jsonCategoryName;
    }
}
