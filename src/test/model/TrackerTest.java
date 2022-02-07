package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackerTest {
    private Tracker testTracker;

    @BeforeEach
    void runBefore() {
        testTracker = new Tracker();
        testTracker.setTotalBudget(1200);
        testTracker.setBudgetNotifcation(200);
        testTracker.newCategory("Clothes", 100);
    }

    @Test
    void testSetTotalBudget() {
        testTracker.setTotalBudget(4000);
        assertEquals(4000, testTracker.getTotalBudget());

        testTracker.setTotalBudget(0);
        assertEquals(0, testTracker.getTotalBudget());
    }

    @Test
    void testSetBudgetNotificaiton() {
        testTracker.setBudgetNotifcation(500);
        assertEquals(500, testTracker.getBudgetNotification());

        testTracker.setBudgetNotifcation(0);
        assertEquals(0, testTracker.getBudgetNotification());
    }

    @Test
    void testOneNewCategory() {
        testTracker.newCategory("Groceries", 1000);
        assertEquals("Clothes", testTracker.getCategories().get(0).getCategoryName());
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(2, testTracker.getCategories().size());
    }

    @Test
    void testRepeatCategory() {
        testTracker.newCategory("Groceries", 1000);
        assertEquals("Clothes", testTracker.getCategories().get(0).getCategoryName());
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(2, testTracker.getCategories().size());

        testTracker.newCategory("Groceries", 250);
        assertEquals("Clothes", testTracker.getCategories().get(0).getCategoryName());
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(2, testTracker.getCategories().size());
    }

    @Test
    void testRepeatThenNewCategory() {
        testTracker.newCategory("Groceries", 1000);
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(2, testTracker.getCategories().size());

        testTracker.newCategory("Groceries", 250);
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(2, testTracker.getCategories().size());

        testTracker.newCategory("UBC Textbooks", 1);
        assertEquals("Clothes", testTracker.getCategories().get(0).getCategoryName());
        assertEquals("Groceries", testTracker.getCategories().get(1).getCategoryName());
        assertEquals("UBC Textbooks", testTracker.getCategories().get(2).getCategoryName());
        assertEquals(100, testTracker.getCategories().get(0).getBudget());
        assertEquals(1000, testTracker.getCategories().get(1).getBudget());
        assertEquals(1, testTracker.getCategories().get(2).getBudget());
        assertEquals(3, testTracker.getCategories().size());
    }

    @Test
    void testRemoveCategory() {
        testTracker.removeCategory("Clothes");
        assertTrue(testTracker.getCategories().isEmpty());
        assertTrue(testTracker.getCategoryNames().isEmpty());
    }

    @Test
    void testAddThenRemoveCategory() {
        testTracker.newCategory("Textbooks", 500);
        assertEquals(2, testTracker.getCategories().size());
        assertEquals(2, testTracker.getCategoryNames().size());
        testTracker.removeCategory("Textbooks");
        assertEquals(1, testTracker.getCategories().size());
        assertEquals(1, testTracker.getCategoryNames().size());
    }

    @Test
    void testRemoveWithExpense() {
        testTracker.addExpense("Clothes", "Shirt", 10);
        testTracker.removeCategory("Clothes");
        assertEquals(1, testTracker.getCategories().size());
        assertEquals(1, testTracker.getCategoryNames().size());

        testTracker.addExpense("Clothes", "Shirt", 10);
        testTracker.removeCategory("Clothes");
        assertEquals(2, testTracker.getExpenses().size());
        assertEquals(1, testTracker.getCategories().size());
        assertEquals(1, testTracker.getCategoryNames().size());
    }

    @Test
    void testRemoveFail() {
        testTracker.removeCategory("Food");
        assertEquals(1, testTracker.getCategories().size());
        assertEquals(1, testTracker.getCategoryNames().size());
    }

    @Test
    void testAddExpenseRemoveCatgeory() {
        testTracker.newCategory("Food", 500);
        testTracker.addExpense("Food", "Carrots", 30);
        assertEquals(2, testTracker.getCategories().size());
        assertEquals(2, testTracker.getCategoryNames().size());
        testTracker.removeCategory("Clothes");
        assertEquals(1, testTracker.getCategories().size());
        assertEquals(1, testTracker.getCategoryNames().size());
        assertEquals("Food", testTracker.getCategories().get(0).getCategoryName());
    }

    @Test
    void addOneExpense() {
        testTracker.addExpense("Clothes", "Shirt", 100);
        assertEquals(1, testTracker.getExpenses().size());
        assertEquals("Clothes", testTracker.getExpenses().get(0).getCategoryName());
        assertEquals("Shirt", testTracker.getExpenses().get(0).getItemName());
        assertEquals(100, testTracker.getExpenses().get(0).getMoneySpent());
    }

    @Test
    void addMultipleExpenses() {
        testTracker.addExpense("Clothes", "Shirt", 100);
        testTracker.addExpense("Clothes", "Pants", 1);
        assertEquals(2, testTracker.getExpenses().size());
        assertEquals("Clothes", testTracker.getExpenses().get(0).getCategoryName());
        assertEquals("Shirt", testTracker.getExpenses().get(0).getItemName());
        assertEquals(100, testTracker.getExpenses().get(0).getMoneySpent());

        assertEquals("Clothes", testTracker.getExpenses().get(1).getCategoryName());
        assertEquals("Pants", testTracker.getExpenses().get(1).getItemName());
        assertEquals(1, testTracker.getExpenses().get(1).getMoneySpent());
    }

    @Test
    void addMultipleExpensesDifferentCategories() {
        testTracker.newCategory("Textbooks", 600);
        testTracker.addExpense("Clothes", "Shirt", 100);
        testTracker.addExpense("Textbooks", "CPSC", 200);
        assertEquals(2, testTracker.getExpenses().size());
        assertEquals("Clothes", testTracker.getExpenses().get(0).getCategoryName());
        assertEquals("Shirt", testTracker.getExpenses().get(0).getItemName());
        assertEquals(100, testTracker.getExpenses().get(0).getMoneySpent());

        assertEquals("Textbooks", testTracker.getExpenses().get(1).getCategoryName());
        assertEquals("CPSC", testTracker.getExpenses().get(1).getItemName());
        assertEquals(200, testTracker.getExpenses().get(1).getMoneySpent());

        assertEquals(100,testTracker.getCategories().get(0).getAmountSpent());
        assertEquals(200,testTracker.getCategories().get(1).getAmountSpent());
    }

    @Test
    void addExpenseButNoCategory() {
        testTracker.addExpense("Textbook", "CPSC", 100);
        assertEquals(0, testTracker.getExpenses().size());
    }

    @Test
    void failedAddExpenseThenAddCategory() {
        testTracker.addExpense("Textbook", "CPSC", 100);
        assertEquals(0, testTracker.getExpenses().size());

        testTracker.newCategory("Textbook", 500);
        testTracker.addExpense("Textbook", "CPSC", 100);
        assertEquals(1, testTracker.getExpenses().size());
        assertEquals("Textbook", testTracker.getExpenses().get(0).getCategoryName());
        assertEquals("CPSC", testTracker.getExpenses().get(0).getItemName());
        assertEquals(100, testTracker.getExpenses().get(0).getMoneySpent());
    }

    @Test
    void addExpenseNearBudgetUpperBound() {
        testTracker.addExpense("Clothes", "Shirt", 1200);
        assertTrue(testTracker.notifyNearBudget());
        assertFalse(testTracker.notifyOverBudget());
    }

    @Test
    void addExpenseNearBudgetLowerBound() {
        testTracker.addExpense("Clothes", "Shirt", 1000);
        assertTrue(testTracker.notifyNearBudget());
    }

    @Test
    void addExpenseOverBudget() {
        testTracker.addExpense("Clothes", "Shirt", 1201);
        assertFalse(testTracker.notifyNearBudget());
        assertTrue(testTracker.notifyOverBudget());
    }

    @Test
    void testShowAllExpenses() {
        testTracker.newCategory("Textbooks", 600);
        testTracker.addExpense("Clothes", "Shirt", 50);
        testTracker.addExpense("Clothes", "Shirt", 45);
        testTracker.addExpense("Textbooks", "CPSC", 90);
        testTracker.addExpense("Textbooks", "Math", 90);
        assertEquals(4,testTracker.getExpenses().size());
        testTracker.showAllExpenses();
    }

    @Test
    void testShowExpensesForCategory() {
        testTracker.newCategory("Textbooks", 600);
        testTracker.addExpense("Clothes", "Shirt", 50);
        testTracker.addExpense("Clothes", "Shirt", 45);
        testTracker.addExpense("Textbooks", "CPSC", 90);
        testTracker.addExpense("Textbooks", "Math", 90);
        testTracker.showExpensesForCategory("Textbooks");
        assertTrue(testTracker.doesCategoryExist("Textbooks"));
        assertFalse(testTracker.doesCategoryExist("Groceries"));
        assertFalse(testTracker.isCategoryUsed("Groceries"));
        assertTrue(testTracker.isCategoryUsed("Textbooks"));
    }

    @Test
    void testShowAllCategories() {
        testTracker.newCategory("Textbooks", 600);
        testTracker.newCategory("Food", 600);
        assertEquals(3,testTracker.getCategories().size());
        testTracker.showAllCategories();
    }

    @Test
    void testAnnouncements() {
        testTracker.addExpense("Clothes", "Shirt", 200);
        testTracker.showBudgetNotifcation();
        testTracker.showTotalSpent();
        testTracker.showTotalBudget();
    }
}


