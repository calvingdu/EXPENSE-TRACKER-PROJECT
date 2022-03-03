package persistence;

import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests inspired by WorkRoom Workshop example
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/DoesntExist.tracker.json");
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
            assertEquals(0, tr.getCategories().size());
            assertEquals(0, tr.getExpenses().size());
            assertEquals(0, tr.getCategoryNames().size());
            assertEquals(1000, tr.getTotalBudget());
            assertEquals(100, tr.getBudgetNotification());
            assertEquals(0, tr.getTotalSpent());
            assertEquals(0, tr.getAmountLeftInBudget());
            assertEquals(0, tr.getAmountOverBudget());
        } catch (IOException e) {
            fail("Should read file");
        }
    }

    @Test
    void testReaderGeneralTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            Tracker tr = reader.read();
            checkTrackerGeneral(tr);

        } catch (IOException e) {
            fail("Should read file");
        }
    }
}



