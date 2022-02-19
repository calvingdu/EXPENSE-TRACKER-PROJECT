package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single expense
public class Expense implements Writable {
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

    // EFFECTS: creates jsonObject of expense
    public JSONObject toJson() {
        JSONObject jsonExpense = new JSONObject();
        jsonExpense.put("category", categoryName);
        jsonExpense.put("item", itemName);
        jsonExpense.put("amount", moneySpent);
        return jsonExpense;
    }


}
