package persistence;

import org.json.JSONObject;

// got code from WorkRoomApp example
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
