package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTest {
    private Expense testExpense;
    private Category groceries;

    @Test
    void testExpense() {
        groceries = new Category("Groceries", 500);
        testExpense = new Expense(groceries, "Carrots", 25);

        assertEquals(groceries, testExpense.getCategory());
        assertEquals(25, testExpense.getMoneySpent());
    }
}
