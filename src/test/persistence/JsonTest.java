package persistence;

import model.Category;
import model.Expense;
import model.Tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Lots of code inspired by WorkRoom Workshop
public class JsonTest {

    // EFFECTS: checks tracker in specifics to a pre-made tracker
    void checkTrackerGeneral(Tracker tr) {
        assertEquals(2, tr.getCategories().size());
        assertEquals(2, tr.getExpenses().size());
        assertEquals(2, tr.getCategoryNames().size());
        checkExpense("groceries", "carrots", 100,
                tr.getExpenses().get(0));
        checkExpense("food", "burger", 75,
                tr.getExpenses().get(1));
        checkCategory("groceries", 300, 100, 100, 200, 0,
                tr.getCategories().get(0));
        checkCategory("food", 50, 10, 75, -25, 25,
                tr.getCategories().get(1));
        assertTrue(tr.getCategoryNames().contains("groceries"));
        assertTrue(tr.getCategoryNames().contains("food"));
        checkSettings(1000, 100, 175, 825, 0, tr);
    }

    // EFFECTS: checks if expense is equivalent to expense in tracker
    void checkExpense(String name, String item, double amount, Expense expense) {
        assertEquals(name, expense.getCategoryName());
        assertEquals(item, expense.getItemName());
        assertEquals(amount, expense.getAmount());
    }

    // EFFECTS: checks if category is equivalent to category in tracker
    void checkCategory(String name, double amount, double notif, double amountspent, double amountleft,
                       double amountover, Category c) {
        assertEquals(name, c.getCategoryName());
        assertEquals(amount, c.getCategoryBudget());
        assertEquals(notif, c.getCategoryBudgetNotification());
        assertEquals(amountspent, c.getCategoryAmountSpent());
        assertEquals(amountover, c.getCategoryAmountOverBudget());
        assertEquals(amountleft, c.getCategoryAmountLeftInBudget());
    }

    // EFFECTS: checks if settings is equivalent to settings in tracker
    void checkSettings(double budget, double notif, double spent,   double amountleft, double amountover, Tracker tr) {
        assertEquals(budget, tr.getTotalBudget());
        assertEquals(spent, tr.getTotalSpent());
        assertEquals(notif, tr.getBudgetNotification());
        assertEquals(amountover, tr.getAmountOverBudget());
        assertEquals(amountleft, tr.getAmountLeftInBudget());
    }

}
