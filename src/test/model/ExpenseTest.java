package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {
    private Expense testExpense;
    private Category groceries;

    @Test
    void testExpense() {
        groceries = new Category("Groceries", 500);
        testExpense = new Expense("Groceries", "Carrots", 25);

        assertEquals("Groceries", testExpense.getCategoryName());
        assertEquals("Carrots", testExpense.getItemName());
        assertEquals(25, testExpense.getMoneySpent());
    }
}
