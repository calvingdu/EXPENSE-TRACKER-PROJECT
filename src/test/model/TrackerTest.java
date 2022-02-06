package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void addOneExpense(){
        testTracker.addExpense(Clothes, "Shirt");
    }
}

