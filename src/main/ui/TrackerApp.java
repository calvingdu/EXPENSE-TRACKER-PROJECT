package ui;




import model.Category;
import model.Expense;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Expense tracker and budget tracking application
public class TrackerApp {
    private Tracker tracker;
    private Scanner input;
    private String specificccategoryname;
    private String categoryname;
    private Category category;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tracker.json";

    // private static final String JSON_STORE = "./data/.tracker.json";

    //EFFECTS: Runs the Tracker App
    public TrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }
    // init and runTracker have a lot of inspiration from the TellerApp used as an example

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddExpense();
        } else if (command.equals("s")) {
            doShowSpending();
        } else if (command.equals("e")) {
            doShowExpenses();
        } else if (command.equals("c")) {
            doShowCategoryExpenses();
        } else if (command.equals("o")) {
            doOptions();
        } else if (command.equals("save")) {
            saveTracker();
        } else if (command.equals("load")) {
            loadTracker();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes tracker
    private void init() {
        tracker = new Tracker();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Upload saved file?");
        displayYesOrNo();
        if (acceptLoadFile()) {
            loadTracker();
        } else {
            System.out.println("Let's do some setup!");
            doSetTotalBudget();
            doSetBudgetNotification();
            System.out.println("We'll start with one category but be sure to add more for efficient tracking!");
            doNewCategory();
        }
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Expense");
        System.out.println("\ts -> Show Spending");
        System.out.println("\te -> Show All Expenses");
        System.out.println("\tc -> Show Expenses in Category");
        System.out.println("\to -> Options");
        System.out.println("\tsave -> Save Tracker File");
        System.out.println("\tload -> Load Tracker File");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Adds an expense given a category name, item name and expense amount
    private void doAddExpense() {
        System.out.print("Enter category for expense: ");
        categoryname = doSelectCategoryName();
        if (tracker.doesCategoryExist(categoryname)) {
            category = tracker.findCategory(categoryname);
            System.out.print("Enter item name for expense: ");
            String itemName = doAddItemName();
            System.out.print("Enter expense amount: ");
            double amount = doAddExpenseAmount();

            tracker.addExpense(categoryname, itemName, amount);
            System.out.println("Successfully added expense: " + categoryname + "/" + itemName + "/$" + amount);

            expenseNotifications();
        } else {
            doMakeNewCategoryFromExpense();
        }
    }

    // EFFECTS: if user is over budget, near budget in either the total or category
    // they will be notified, otherwise, nothing happens
    private void expenseNotifications() {
        if (tracker.notifyOverBudget()) {
            System.out.println("You are over budget by $" + tracker.getAmountOverBudget());
        }
        if (tracker.notifyNearBudget()) {
            System.out.println("Warning: You have $" + tracker.getAmountLeftInBudget() + " left in your budget");
        }
        if (category.notifyNearCategoryBudget(category)) {
            System.out.println("Warning: You have $" + category.getCategoryAmountLeftInBudget() + " left in your "
                    + categoryname + " Category");
        }
        if (category.notifyOverCategoryBudget(category)) {
            System.out.println("You are over budget by $" + category.getCategoryAmountOverBudget() + " in the "
                    + categoryname + " category");
        }
    }

    // MODIFIES: this
    // EFFECTS: Accepts user input for a yes or no question to make a category or not
    private void doMakeNewCategoryFromExpense() {
        System.out.println("This category does not exist, do you want to make a new one?");
        displayYesOrNo();
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("y")) {
            doNewCategory();
        } else {
            System.out.println("Returning back to main menu");
        }

    }


    // MODIFIES: this
    // EFFECTS: Returns string of category name if a category with the inputted name if it exists
    // otherwise return an empty string
    private String doSelectCategoryName() {
        doShowAllCategories();
        String categoryName = "";

        categoryName = input.next();

        if (tracker.doesCategoryExist(categoryName)) {
            System.out.println("Category Selected: " + categoryName);
            return categoryName;
        } else {
            return "";
        }
    }


    // MODIFIES: this
    // EFFECTS: Prompts user to add a name for the item they are adding an expense for and returns that name
    private String doAddItemName() {
        String itemName = input.next();
        return itemName;
    }

    // MODIFIES: this
    // EFFECTS: returns amount of inputted amount
    private double doAddExpenseAmount() {
        double amount = input.nextDouble();
        return amount;
    }

    // EFFECTS: Shows the amount spent and how much is left in the budget
    private void doShowSpending() {
        System.out.println("Spent: $" + tracker.getTotalSpent());
        System.out.println("Amount left in budget: $" + tracker.getAmountLeftInBudget());
    }

    // EFFECTS: shows all expenses
    private void doShowExpenses() {
        for (Expense expense : tracker.getExpenses()) {
            System.out.println("You bought " + expense.getItemName() + " for $" + expense.getAmount() + " in the "
                    + expense.getCategoryName() + " category");
        }
    }

    // EFFECTS: shows all expenses in the given category
    private void doShowCategoryExpenses() {
        System.out.println("Input Category: ");
        String categoryName = doSelectCategoryName();

        if (tracker.doesCategoryExist(categoryName)) {
            Category category = tracker.findCategory(categoryName);
            for (Expense expense : tracker.getExpenses()) {
                if (expense.getCategoryName().equals(categoryName)) {
                    System.out.println("You bought " + expense.getItemName()
                            + " for $" + expense.getAmount() + " in the "
                            + expense.getCategoryName() + " category");
                }
            }
        } else {
            System.out.println("This category does not exist");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays options and prompts user to continue through the options menu
    private void doOptions() {
        String command = null;

        displayOptionsMenu();
        command = input.next();
        command = command.toLowerCase();
        processOptionsCommand(command);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processOptionsCommand(String command) {
        if (command.equals("c")) {
            doCategoryOptions();
        } else if (command.equals("b")) {
            doSetTotalBudget();
        } else if (command.equals("n")) {
            doSetBudgetNotification();
        } else if (command.equals("s")) {
            doShowCurrentSettings();
        } else if (command.equals("m")) {
            System.out.println("");
        } else {
            System.out.println("Selection not valid, returning back to Main Menu");
        }
    }

    // EFFECTS: displays Options menu to user
    private void displayOptionsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> Category Options");
        System.out.println("\tb -> Set Total Budget");
        System.out.println("\tn -> Set Budget Notification");
        System.out.println("\ts -> Show current settings");
        System.out.println("\tm -> Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: displays options and prompts user to continue through the category options menu
    private void doCategoryOptions() {
        String command = null;

        displayCategoryOptionsMenu();
        command = input.next();
        command = command.toLowerCase();
        processCategoryOptionsCommand(command);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to change total budget and then changes the budget to the double they select
    private void doSetTotalBudget() {
        System.out.println("Input amount to set your total budget: ");
        double amount = input.nextDouble();
        tracker.setTotalBudget(amount);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to change budget notification and then changes it to the double they select
    private void doSetBudgetNotification() {
        System.out.println("Input amount to be notified at: ");
        double amount = input.nextDouble();
        tracker.setBudgetNotification(amount);
    }

    // EFFECTS: displays what current budget and budget notification is
    private void doShowCurrentSettings() {
        System.out.println("Total budget is: $" + tracker.getTotalBudget());
        System.out.println("You will be notified when you have: $" + tracker.getBudgetNotification() + " left "
                + "in your budget");
    }

    // EFFECTS: displays CategoryOptions menu to user
    private void displayCategoryOptionsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tn -> New Category");
        System.out.println("\tr -> Remove Category");
        System.out.println("\ts -> Show all Categories");
        System.out.println("\tc -> Specific Category Options");
        System.out.println("\tm -> Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCategoryOptionsCommand(String command) {
        if (command.equals("n")) {
            doNewCategory();
        } else if (command.equals("r")) {
            doRemoveCategory();
        } else if (command.equals("s")) {
            doShowAllCategories();
        } else if (command.equals("c")) {
            doSelectCategoryForOptions();
        } else if (command.equals("m")) {
            System.out.println("");
        } else {
            System.out.println("Selection not valid, returning back to Main Menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to create a new category of inputted name with inputted budget then does so
    private void doNewCategory() {
        String name = doNewCategoryName();
        double amount = doNewCategoryAmount();

        if (tracker.doesCategoryExist(name)) {
            System.out.println("This category already exists");
        } else {
            tracker.newCategory(name, amount);
            System.out.println("Successfully created new category: " + name + " with budget $" + amount);
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input a new category name and return the inputted string
    private String doNewCategoryName() {
        System.out.println("Input new category name: ");
        String name = input.next();
        return name;
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input the new category's budget and return the inputted number
    private double doNewCategoryAmount() {
        System.out.println("Input new category budget: ");
        double amount = input.nextDouble();
        return amount;
    }

    // MODIFIES: this
    // EFFECTS: Takes in string input and removes the category with that name
    private void doRemoveCategory() {
        doShowAllCategories();
        System.out.println("Input category to remove");
        String name = input.next();
        if (tracker.doesCategoryExist(name)) {
            if (tracker.isCategoryUsed(name)) {
                System.out.println("This category is being used and cannot be removed");
            } else {
                tracker.removeCategory(name);
                System.out.println("Successfully removed category: " + name);
            }
        } else {
            System.out.println("This category does not exist");
        }

    }

    // EFFECTS: Shows all categories that have been created
    private void doShowAllCategories() {
        System.out.println("Categories: ");
        for (Category category : tracker.getCategories()) {
            System.out.println(category.getCategoryName());
        }
    }

    // MODIFIES: this
    // EFFECTS: returns category of the category name inputted
    private String doSelectCategory() {
        String selection = "";

        selection = input.next();

        if (tracker.doesCategoryExist(selection)) {
            Category category = tracker.findCategory(selection);
            System.out.println("Category selected: " + selection);
            return selection;
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays categories that exist and gets user's selection of
    // category of inputted name
    private void doSelectCategoryForOptions() {
        doShowAllCategories();
        System.out.println("Select category: ");
        String name = doSelectCategory();
        specificccategoryname = name;
        doSpecificCategoryOptions();
    }

    // MODIFIES: this
    // EFFECTS: takes in user's input
    private void doSpecificCategoryOptions() {
        String command = null;
        displayScoMenu();
        command = input.next();
        command = command.toLowerCase();
        processScoCommand(command);
    }

    // EFFECTS: displays Specific Category Options Menu and also the category's current budget
    // and budget notification
    private void displayScoMenu() {
        System.out.println("Category: " + specificccategoryname + " has budget of $"
                + tracker.showCategoryBudget(specificccategoryname));
        System.out.println("Category: " + specificccategoryname + " has budget notification of $"
                + tracker.showCategoryBudgetNotification(specificccategoryname));
        System.out.println("\nSelect from:");
        System.out.println("\tb -> Set Category Budget ");
        System.out.println("\tn -> Set Category Budget Notification");
        System.out.println("\tt -> Change Category Name");
        System.out.println("\tm -> Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processScoCommand(String command) {
        if (command.equals("b")) {
            doSetCategoryBudget();
        } else if (command.equals("n")) {
            doSetCategoryBudgetNotification();
        } else if (command.equals("t")) {
            doSetCategoryName();
        } else if (command.equals("m")) {
            System.out.println("");
        } else {
            System.out.println("Selection not valid, returning back to Main Menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to set a specific category's budget and then changes it to the double input
    private void doSetCategoryBudget() {
        System.out.println("Input Budget amount: ");
        double amount = input.nextDouble();
        tracker.setCategoryBudget(specificccategoryname, amount);
        System.out.println("Set Category: " + specificccategoryname + " budget to $" + amount);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to set a specific category's budget notification and then changes it to the double input
    private void doSetCategoryBudgetNotification() {
        System.out.println("Input Budget Notification amount: ");
        double amount = input.nextDouble();
        tracker.setCategoryBudgetNotification(specificccategoryname, amount);
        System.out.println("Set Category: " + specificccategoryname + " budget notification to $" + amount);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to set a specific category's name to something else
    private void doSetCategoryName() {
        System.out.println("Input new category name: ");
        String name = input.next();
        tracker.changeCategoryName(specificccategoryname, name);
        System.out.println("Changed Category: " + specificccategoryname + " to " + name);
    }

    // EFFECTS: displays menu options to user
    private void displayYesOrNo() {
        System.out.println("\nSelect from:");
        System.out.println("\ty -> Yes");
        System.out.println("\tn -> No");
    }

    // EFFECTS: saves the tracker from file
    private void saveTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(tracker);
            jsonWriter.close();
            System.out.println("Saved Tracker to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Tracker from file
    private void loadTracker() {
        try {
            tracker = jsonReader.read();
            System.out.println("Loaded tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: asks user if they want to load file, if so loads file, if not then don't load file
    public boolean acceptLoadFile() {
        String command = null;
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            return true;
        } else {
            return false;
        }
    }
}









