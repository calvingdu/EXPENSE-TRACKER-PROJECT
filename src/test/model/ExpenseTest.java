package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {
    private Expense testExpense;
    private Category groceries;


    @BeforeEach
    void runBefore() {
        groceries = new Category("Groceries", 500);
        testExpense = new Expense("Groceries", "Carrots", 25);
    }

    @Test
    void testExpense() {
        assertEquals("Groceries", testExpense.getCategoryName());
        assertEquals("Carrots", testExpense.getItemName());
        assertEquals(25, testExpense.getMoneySpent());
    }

    @Test
    void testChangeCategoryName() {
        testExpense.changeCategoryName("Food");
        assertEquals("Food", testExpense.getCategoryName());
        assertEquals("Carrots", testExpense.getItemName());
        assertEquals(25, testExpense.getMoneySpent());
    }
}
