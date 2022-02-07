package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    private Category testCategory;

    @BeforeEach
    void runBefore() {
        testCategory = new Category("Groceries", 500);
        testCategory.setCategoryBudgetNotifcation(50);
    }

    @Test
    void testSetBudget() {
        testCategory.setBudget(250);
        assertEquals(250, testCategory.getBudget());
        assertEquals(250, testCategory.getCategoryAmountLeftInBudget());
        testCategory.setBudget(0);
        assertEquals(0, testCategory.getBudget());
        assertEquals(0, testCategory.getCategoryAmountLeftInBudget());
    }

    @Test
    void testBudgetChangeAfterExpense() {
        testCategory.setBudget(250);
        assertEquals(250, testCategory.getBudget());
        assertEquals(250, testCategory.getCategoryAmountLeftInBudget());

        testCategory.expenseInCategory(50);
        assertEquals(250, testCategory.getBudget());
        assertEquals(200, testCategory.getCategoryAmountLeftInBudget());

        testCategory.setBudget(500);
        assertEquals(500, testCategory.getBudget());
        assertEquals(450, testCategory.getCategoryAmountLeftInBudget());
    }

    @Test
    void testChangeCategoryName() {
        testCategory.changeCategoryName("Food");
        assertEquals("Food", testCategory.getCategoryName());
        testCategory.changeCategoryName("Expenses buying UBC textbooks");
        assertEquals("Expenses buying UBC textbooks", testCategory.getCategoryName());
    }

    @Test
    void testExpenseSmallPurchases() {
        testCategory.expenseInCategory(1);
        assertEquals(1, testCategory.getAmountSpent());
        assertEquals(499, testCategory.getCategoryAmountLeftInBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));

        testCategory.expenseInCategory(250);
        assertEquals(251, testCategory.getAmountSpent());
        assertEquals(249, testCategory.getCategoryAmountLeftInBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseNearBudgetUpperBound() {
        testCategory.expenseInCategory(500);
        assertEquals(500, testCategory.getAmountSpent());
        assertEquals(0, testCategory.getCategoryAmountLeftInBudget());
        assertTrue(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseNearBudgetLowerBound() {
        testCategory.expenseInCategory(450);
        assertEquals(450, testCategory.getAmountSpent());
        assertEquals(50, testCategory.getCategoryAmountLeftInBudget());
        assertTrue(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseOverBudget() {
        testCategory.expenseInCategory(501);
        assertEquals(501, testCategory.getAmountSpent());
        assertEquals(-1, testCategory.getCategoryAmountLeftInBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertTrue(testCategory.notifyOverCategoryBudget(testCategory));

        testCategory.expenseInCategory(499);
        assertEquals(1000, testCategory.getAmountSpent());
        assertEquals(-500, testCategory.getCategoryAmountLeftInBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertTrue(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testCategoryAnnouncements() {
        testCategory.expenseInCategory(200);
        testCategory.showCategoryBudget();
        testCategory.showCategorySpent();
        testCategory.showCategoryBudgetNotification();

    }
}
