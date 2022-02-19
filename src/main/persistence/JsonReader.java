package persistence;

import model.Category;
import model.Expense;
import model.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads tracker from JSON data stored in file
// Much inspiration from the example: Workroom
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Tracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses tracker from JSON object and returns it
    private Tracker parseTracker(JSONObject jsonObject) {
        Tracker tr = new Tracker();
        addTrackerItems(tr, jsonObject);
        return tr;
    }

    // MODIFIES: tr
    // EFFECTS: parses budget settings and expense history from JSON object and adds them to Tracker
    private void addTrackerItems(Tracker tr, JSONObject jsonObject) {
        addExpenses(tr, jsonObject);
        addCategories(tr, jsonObject);
        addCategoryNames(tr, jsonObject);
        addSettings(tr, jsonObject);
    }

    // MODIFIES: tr
    // EFFECTS: parses expense history from JSON object and adds them to Tracker
    private void addExpenses(Tracker tr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(tr, nextExpense);
        }
    }

    // MODIFIES: tr
    // EFFECTS: parses singular expense from JSON object and adds them to tracker
    private void addExpense(Tracker tr, JSONObject jsonObject) {
        String category = jsonObject.getString("category");
        String item = jsonObject.getString("item");
        double amount = jsonObject.getDouble("amount");
        Expense expense = new Expense(category, item, amount);
        tr.parseExpense(expense);
    }

    // MODIFIES: tr
    // EFFECTS: parses categories from JSON object and adds them to Tracker
    private void addCategories(Tracker tr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(tr, nextCategory);
        }
    }

    // MODIFIES: tr
    // EFFECTS: parses singular category from JSON object and adds them to tracker
    private void addCategory(Tracker tr, JSONObject jsonObject) {
        String name = jsonObject.getString("category");
        double budget = jsonObject.getDouble("budget");
        double notification = jsonObject.getDouble("notification");
        double spent = jsonObject.getDouble("spent");
        double left = jsonObject.getDouble("left");
        double over = jsonObject.getDouble("over");
        Category category = new Category(name, budget);
        category.setCategoryBudgetNotification(notification);
        category.setCategorySpent(spent);
        category.setAmountLeftInBudget(left);
        category.setAmountOverBudget(over);
        tr.parseCategory(category);
    }

    // MODIFIES: tr
    // EFFECTS: parses category name from JSON object and adds them to Tracker
    private void addCategoryNames(Tracker tr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("category names");
        for (Object json : jsonArray) {
            JSONObject nextCategoryName = (JSONObject) json;
            addCategoryName(tr, nextCategoryName);
        }
    }

    // MODIFIES: tr
    // EFFECTS: parses singular expense from JSON object and adds them to tracker
    private void addCategoryName(Tracker tr, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        tr.parseCategoryName(name);
    }

    // MODIFIES: tr
    // EFFECTS: parses settings
    private void addSettings(Tracker tr, JSONObject jsonObject) {
        double budget = jsonObject.getDouble("budget");
        double notification = jsonObject.getDouble("notification");
        double spent = jsonObject.getDouble("spent");
        double amountleft = jsonObject.getDouble("left");
        double amountover = jsonObject.getDouble("over");

        tr.setTotalBudget(budget);
        tr.setBudgetNotification(notification);
        tr.setAmountOverBudget(amountover);
        tr.setAmountLeftInBudget(amountleft);
        tr.setTotalSpent(spent);
    }

}
