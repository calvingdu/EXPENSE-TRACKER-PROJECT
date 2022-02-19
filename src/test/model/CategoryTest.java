package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    private Category testCategory;

    @BeforeEach
    void runBefore() {
        testCategory = new Category("Groceries", 500);
        testCategory.setCategoryBudgetNotification(50);
    }

    @Test
    void testSetBudget() {
        testCategory.setBudget(250);
        assertEquals(250, testCategory.getCategoryBudget());
        assertEquals(250, testCategory.getCategoryAmountLeftInBudget());
        testCategory.setBudget(0);
        assertEquals(0, testCategory.getCategoryBudget());
        assertEquals(0, testCategory.getCategoryAmountLeftInBudget());
    }

    @Test
    void testSetBudgetNotification() {
        testCategory.setCategoryBudgetNotification(50);
        assertEquals(50, testCategory.getCategoryBudgetNotification());
        testCategory.setCategoryBudgetNotification(75);
        assertEquals(75, testCategory.getCategoryBudgetNotification());
    }

    @Test
    void testBudgetChangeAfterExpense() {
        testCategory.setBudget(250);
        assertEquals(250, testCategory.getCategoryBudget());
        assertEquals(250, testCategory.getCategoryAmountLeftInBudget());

        testCategory.expenseInCategory(50);
        assertEquals(250, testCategory.getCategoryBudget());
        assertEquals(200, testCategory.getCategoryAmountLeftInBudget());

        testCategory.setBudget(500);
        assertEquals(500, testCategory.getCategoryBudget());
        assertEquals(450, testCategory.getCategoryAmountLeftInBudget());
    }

    @Test
    void testChangeCategoryName() {
        testCategory.changeCategoryName("Food");
        assertEquals("Food", testCategory.getCategoryName());
        testCategory.changeCategoryName("Expenses");
        assertEquals("Expenses", testCategory.getCategoryName());
    }

    @Test
    void testExpenseSmallPurchases() {
        testCategory.expenseInCategory(1);
        assertEquals(1, testCategory.getCategoryAmountSpent());
        assertEquals(499, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(-499,testCategory.getCategoryAmountOverBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));

        testCategory.expenseInCategory(250);
        assertEquals(251, testCategory.getCategoryAmountSpent());
        assertEquals(249, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(-249,testCategory.getCategoryAmountOverBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseNearBudgetUpperBound() {
        testCategory.expenseInCategory(500);
        assertEquals(500, testCategory.getCategoryAmountSpent());
        assertEquals(0, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(0,testCategory.getCategoryAmountOverBudget());
        assertTrue(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseNearBudgetLowerBound() {
        testCategory.expenseInCategory(450);
        assertEquals(450, testCategory.getCategoryAmountSpent());
        assertEquals(50, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(-50,testCategory.getCategoryAmountOverBudget());
        assertTrue(testCategory.notifyNearCategoryBudget(testCategory));
        assertFalse(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testExpenseOverBudget() {
        testCategory.expenseInCategory(501);
        assertEquals(501, testCategory.getCategoryAmountSpent());
        assertEquals(-1, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(1,testCategory.getCategoryAmountOverBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertTrue(testCategory.notifyOverCategoryBudget(testCategory));

        testCategory.expenseInCategory(499);
        assertEquals(1000, testCategory.getCategoryAmountSpent());
        assertEquals(-500, testCategory.getCategoryAmountLeftInBudget());
        assertEquals(500,testCategory.getCategoryAmountOverBudget());
        assertFalse(testCategory.notifyNearCategoryBudget(testCategory));
        assertTrue(testCategory.notifyOverCategoryBudget(testCategory));
    }

    @Test
    void testGetCategoryAmountOverBudget() {
        assertEquals(0,testCategory.getCategoryAmountOverBudget());
        testCategory.expenseInCategory(600);
        assertEquals(100,testCategory.getCategoryAmountOverBudget());
    }

    @Test
    void testSetAmountOverBudget() {
        testCategory.setAmountOverBudget(100);
        assertEquals(100,testCategory.getCategoryAmountOverBudget());
    }

    @Test
    void testSetAmountLeftInBudget() {
        testCategory.setAmountLeftInBudget(200);
        assertEquals(200,testCategory.getCategoryAmountLeftInBudget());
    }

    @Test
    void testSetSpent() {
        testCategory.setCategorySpent(100);
        assertEquals(100, testCategory.getCategoryAmountSpent());
    }
}
