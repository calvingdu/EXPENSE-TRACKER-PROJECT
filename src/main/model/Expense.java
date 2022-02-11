package model;

public class Expense {
    private String categoryName;
    private String itemName;
    private double moneySpent;

    // REQUIRES: name and item has non-zero length, amount >= 0
    // EFFECTS: creates category with a name and a budget
    public Expense(String name, String item, double amount) {
        categoryName = name;
        itemName = item;
        moneySpent = amount;
    }

    // REQUIRES: string name has non-zero length
    // MODIFIES: this
    // EFFECTS: changes expense's category to a new name
    public void changeCategoryName(String name) {
        categoryName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public double getMoneySpent() {
        return moneySpent;
    }
}
