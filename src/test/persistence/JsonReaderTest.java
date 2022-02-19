package persistence;

import model.Category;
import model.Expense;
import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Tracker tr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            Tracker tr = reader.read();
            assertEquals(0,tr.getCategories().size());
            assertEquals(0,tr.getExpenses().size());
            assertEquals(0,tr.getCategoryNames().size());
            assertEquals(1000,tr.getTotalBudget());
            assertEquals(100,tr.getBudgetNotification());
            assertEquals(0,tr.getTotalSpent());
            assertEquals(0,tr.getAmountLeftInBudget());
            assertEquals(0,tr.getAmountOverBudget());

        } catch (IOException e) {
            fail("Should read file");
        }
    }

    @Test
    void testReaderGeneralTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            Tracker tr = reader.read();
            assertEquals(2,tr.getCategories().size());
            assertEquals(2,tr.getExpenses().size());
            assertEquals(2,tr.getCategoryNames().size());
            checkExpense("groceries","carrots",100,
                    tr.getExpenses().get(0));
            checkExpense("food","burger",50,
                    tr.getExpenses().get(1));
            checkCategory("groceries",300,100,100,200,0,
                    tr.getCategories().get(0));
            checkCategory("food",50,10,75,-25,25,
                    tr.getCategories().get(0));
            assertTrue(tr.getCategoryNames().contains("groceries"));
            assertTrue(tr.getCategoryNames().contains("food"));
            checkSettings(1000,100,175,825,0,tr);
        } catch (IOException e) {
            fail("Should read file");
        }
    }

    void checkExpense(String name, String item, double amount, Expense expense) {
        assertEquals(name, expense.getCategoryName());
        assertEquals(item, expense.getItemName());
        assertEquals(amount, expense.getMoneySpent());
    }

    void checkCategory(String name, double amount, double notif, double amountspent, double amountleft,
                       double amountover, Category c) {
        assertEquals(name, c.getCategoryName());
        assertEquals(amount, c.getCategoryBudget());
        assertEquals(notif, c.getCategoryBudgetNotification());
        assertEquals(amountspent, c.getCategoryAmountSpent());
        assertEquals(amountover, c.getCategoryAmountOverBudget());
        assertEquals(amountleft, c.getCategoryAmountLeftInBudget());
    }

    void checkSettings(double budget, double notif, double spent,   double amountleft, double amountover, Tracker tr) {
        assertEquals(budget, tr.getTotalBudget());
        assertEquals(spent, tr.getTotalSpent());
        assertEquals(notif, tr.getBudgetNotification());
        assertEquals(amountover, tr.getAmountOverBudget());
        assertEquals(amountleft, tr.getAmountLeftInBudget());
    }
}

