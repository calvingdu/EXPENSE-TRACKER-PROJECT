package model;

public class Expense {
    private Category category;
    private String itemName;
    private double moneySpent;

    // REQUIRES: category exists, item has non-zero length, amount >= 0
    // EFFECTS: creates category with a name and a budget
    public Expense(Category name, String item, double amount) {
        category = name;
        itemName = item;
        moneySpent = amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public double getMoneySpent() {
        return moneySpent;
    }
}
