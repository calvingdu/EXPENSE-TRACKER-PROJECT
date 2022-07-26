package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashSet;

// Represents the entire tracker, that keeps track of budgets, expenses, categories, ect
public class Tracker implements Writable {
    private ArrayList<Expense> expenses;
    private ArrayList<Category> categories;
    private HashSet<String> categoryNames;
    private double totalSpent;
    private double totalBudget;
    private double budgetNotification;
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
    // EFFECTS: Sets total budget and changes amountLeft to be budget - spent and adds it to event log
    public void setTotalBudget(double amount) {
        totalBudget = amount;
        amountLeftInBudget = totalBudget - totalSpent;
        amountOverBudget = totalSpent - totalBudget;
        if (amountOverBudget <= 0) {
            amountOverBudget = 0;
        }
        EventLog.getInstance().logEvent(new Event("Set Budget: $" + amount));
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: set the amount of $ the user will be notified at when they have that amount left
    // in their budget and adds it to event log
    public void setBudgetNotification(double amount) {
        budgetNotification = amount;
        EventLog.getInstance().logEvent(new Event("Set Notification Amount: $" + amount));
    }

    // REQUIRES: name and item string length is non-zero, amount >= 0
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
            amountOverBudget = totalSpent - totalBudget;
            if (amountOverBudget < 0) {
                amountOverBudget = 0;
            }
            notifyNearBudget();
            notifyOverBudget();
            category.expenseInCategory(amount);
            category.notifyNearCategoryBudget(category);
            category.notifyOverCategoryBudget(category);
            logEvents(categoryName, item, amount);
        }
    }

    // MODIFIES: EventLog
    // EFFECTS: Logs Events to EventLog
    private void logEvents(String categoryName, String item, double amount) {
        EventLog.getInstance().logEvent(new Event(
                "Added Expense: " + categoryName + " // " + item + " // $" + amount));
        if (notifyOverBudget()) {
            EventLog.getInstance().logEvent(new Event("Notified Over Budget"));
        }
        if (notifyNearBudget()) {
            EventLog.getInstance().logEvent(new Event("Notified Near Budget"));
        }
    }

    // REQUIRES: name length is non-zero, amount >= 0
    // MODIFIES: this
    // EFFECTS: Creates a new category if it doesn't already exist and adds it to event log
    public void newCategory(String name, double amount) {
        Category category = new Category(name, amount);
        if (!doesCategoryExist(name)) {
            categories.add(category);
            categoryNames.add(name);
            EventLog.getInstance().logEvent(new Event("Added Category: " + name));
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
    // MODIFIES: this
    // EFFECTS: Sets a specific category's budget
    public void setCategoryBudget(String name, double amount) {
        category = findCategory(name);
        category.setBudget(amount);
    }

    // REQUIRES: name length is non-zero and amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets a specific category's budget notification
    public void setCategoryBudgetNotification(String name, double amount) {
        category = findCategory(name);
        category.setCategoryBudgetNotification(amount);
    }


    // REQUIRES: categoryName length is non-zero
    // EFFECTS: returns true if category exists, false otherwise
    public boolean doesCategoryExist(String categoryName) {
        if (categoryNames.contains(categoryName)) {
            return true;
        }
        return false;
    }

    // REQUIRES: name length is non-zero
    // EFFECTS: Returns category in list of categories given its name if it exists,
    // otherwise return null
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
    // EFFECTS: changes original category name to changed named, taking making the lists of category and
    // category name line up and also every expense associated with it
    public void changeCategoryName(String original, String changed) {
        if (doesCategoryExist(original)) {
            category = findCategory(original);
            category.changeCategoryName(changed);
            categoryNames.remove(original);
            categoryNames.add(changed);
            for (Expense expense : expenses) {
                if (expense.getCategoryName().equals(original)) {
                    expense.changeCategoryName(changed);
                }
            }
        }
    }

    // EFFECTS: Notifies user when amount left in budget is less than notification level but more than 0
    public boolean notifyNearBudget() {
        if (amountLeftInBudget <= budgetNotification) {
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
    // EFFECTS: returns amount left in budget in the category of name
    public double showCategoryAmountLeftInBudget(String name) {
        Category category = findCategory(name);
        double amount = category.getCategoryAmountLeftInBudget();
        return amount;

    }

    // REQUIRES: name has non-zero length
    // EFFECTS: returns budget of category matching input
    public double showCategoryBudget(String name) {
        Category category = findCategory(name);
        double budget = category.getCategoryBudget();
        return budget;
    }

    // REQUIRES: name has non-zero length
    // EFFECTS: returns budget notification of category matching input
    public double showCategoryBudgetNotification(String name) {
        Category category = findCategory(name);
        double notification = category.getCategoryBudgetNotification();
        return notification;
    }


    public double getBudgetNotification() {
        return budgetNotification;
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

    // MODIFIES: this
    // EFFECTS: adds expense to list of expenses
    public void parseExpense(Expense expense) {
        expenses.add(expense);
    }

    // MODIFIES: this
    // EFFECTS: adds category to list of categories
    public void parseCategory(Category category) {
        categories.add(category);
    }

    // MODIFIES: this
    // EFFECTS: adds category name to list of category names
    public void parseCategoryName(String name) {
        categoryNames.add(name);
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets total spent in tracker
    public void setTotalSpent(double amount) {
        totalSpent = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the amount of money spent over budget
    public void setAmountOverBudget(double amount) {
        amountOverBudget = amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Sets amount left in budget
    public void setAmountLeftInBudget(double amount) {
        amountLeftInBudget = amount;
    }

    // EFFECTS: returns tracker as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        json.put("categories", categoriesToJson());
        json.put("category names", categoryNamesToJson());
        json.put("budget", totalBudget);
        json.put("notification", budgetNotification);
        json.put("spent", totalSpent);
        json.put("left", amountLeftInBudget);
        json.put("over", amountOverBudget);
        return json;
    }

    // EFFECTS: returns expenses in tracker as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArrayExpenses = new JSONArray();

        for (Expense e : expenses) {
            jsonArrayExpenses.put(e.toJson());
        }

        return jsonArrayExpenses;
    }

    // EFFECTS: returns categories in tracker as a JSON array
    private JSONArray categoriesToJson() {
        JSONArray jsonArrayCategories = new JSONArray();

        for (Category c : categories) {
            jsonArrayCategories.put(c.toJson());
        }
        return jsonArrayCategories;
    }

    // EFFECTS: returns category Names in tracker as a JSON array
    private JSONArray categoryNamesToJson() {
        JSONArray jsonArrayCategoryNames = new JSONArray();

        for (Category c : categories) {
            jsonArrayCategoryNames.put(c.toJsonCategoryName());
        }
        return jsonArrayCategoryNames;
    }
}
