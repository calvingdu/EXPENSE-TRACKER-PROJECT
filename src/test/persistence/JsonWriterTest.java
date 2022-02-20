package persistence;

import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Lots of code inspired by WorkRoom Workshop
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Tracker tr = new Tracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.tracker.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            Tracker tr = new Tracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.tracker.json");
            writer.open();
            writer.write(tr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.tracker.json");
            tr = reader.read();
            assertEquals(0,tr.getCategories().size());
            assertEquals(0,tr.getExpenses().size());
            assertEquals(0,tr.getCategoryNames().size());
            assertEquals(0,tr.getTotalBudget());
            assertEquals(0,tr.getBudgetNotification());
            assertEquals(0,tr.getTotalSpent());
            assertEquals(0,tr.getAmountLeftInBudget());
            assertEquals(0,tr.getAmountOverBudget());
        } catch (IOException e) {
            fail("Exception should be thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            Tracker tr = new Tracker();
            makeTracker(tr);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.tracker.json");
            writer.open();
            writer.write(tr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.tracker.json");
            tr = reader.read();
            checkTrackerGeneral(tr);

        } catch (IOException e) {
            fail("Should read file");
        }
    }

    // MODIFIES: tr
    // EFFECTS: creates a tracker to be used for testing
    void makeTracker(Tracker tr) {
        tr.newCategory("groceries",300);
        tr.getCategories().get(0).setCategoryBudgetNotification(100);
        tr.newCategory("food",50);
        tr.getCategories().get(1).setCategoryBudgetNotification(10);

        tr.addExpense("groceries","carrots",100);
        tr.addExpense("food","burger",75);

        tr.setTotalBudget(1000);
        tr.setBudgetNotification(100);
    }



}
