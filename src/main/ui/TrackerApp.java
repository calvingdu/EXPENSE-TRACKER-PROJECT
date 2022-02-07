package ui;

public class TrackerApp {

    //EFFECTS: Runs the Tracker App
    public TrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes tracker
    private void init() {
        // STUB
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: co
}





