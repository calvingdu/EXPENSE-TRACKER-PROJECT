package ui;

import model.Category;
import model.Expense;
import model.Tracker;

import java.util.Scanner;

public class TrackerApp {
    private Tracker tracker;
    private Scanner input;
    private String specificccategoryname;

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
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Expense");
        System.out.println("\ts -> Show Spending");
        System.out.println("\te -> Show All Expenses");
        System.out.println("\tc -> Show Expenses in Category");
        System.out.println("\to -> Options");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: Adds an expense given a category name, item name and expense amount
    private void doAddExpense() {
        System.out.print("Enter category for expense: ");
        String categoryName = doSelectCategoryName();
        Category category = tracker.findCategory(categoryName);
        System.out.print("Enter item name for expense: ");
        String itemName = doAddItemName();
        System.out.print("Enter expense amount: ");
        double amount = doAddExpenseAmount();

        tracker.addExpense(categoryName, itemName, amount);
        System.out.println("Successfully added expense: " + categoryName + "/" + itemName + "/$" + amount);

        if (tracker.notifyOverBudget()) {
            System.out.println("Warning: You have $" + tracker.getAmountLeftInBudget() + " left in your budget");
        }

        if (tracker.notifyNearBudget()) {
            System.out.println("You are over budget by $" + tracker.getAmountOverBudget());
        }

        if (category.notifyNearCategoryBudget(category)) {
            System.out.println("Warning: You have $" + category.getCategoryAmountLeftInBudget() + " left in your "
                    + categoryName + " Category");
        }

        if (category.notifyOverCategoryBudget(category)) {
            System.out.println("You are over budget by $" + category.getCategoryAmountOverBudget() + " in the "
                    + categoryName + " category");
        }
    }

    // MODIFIES: this
    // EFFECTS: Returns string of category name if a category with the inputted name exists
    private String doSelectCategoryName() {
        doShowAllCategories();
        String categoryName = "";

        categoryName = input.next();

        if (tracker.doesCategoryExist(categoryName)) {
            System.out.println("Category Selected: " + categoryName);
            return categoryName;
        } else {
            System.out.println("This category does not exist");
            return null;
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
            System.out.println("You bought " + expense.getItemName() + " for " + expense.getMoneySpent() + " in the "
                    + expense.getCategoryName() + " category.");
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
                            + " for $" + expense.getMoneySpent() + " in the "
                            + expense.getCategoryName() + " category.");
                }
            }
        } else {
            System.out.println("This category does not exist.");
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
        Double amount = input.nextDouble();
        tracker.setTotalBudget(amount);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to change budget notification and then changes it to the double they select
    private void doSetBudgetNotification() {
        System.out.println("Input amount to be notified at: ");
        Double amount = input.nextDouble();
        tracker.setBudgetNotifcation(amount);
    }

    // EFFECTS: displays what current budget and budget notification is
    private void doShowCurrentSettings() {
        System.out.println("Total budget is: " + tracker.getTotalBudget());
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
    // EFFECTS: creates a new category of inputted name with inputted budget
    private void doNewCategory() {
        String name = doNewCategoryName();
        Double amount = doNewCategoryAmount();

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
    private Double doNewCategoryAmount() {
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
        if (tracker.isCategoryUsed(name)) {
            System.out.println("This category is being used and cannot be removed.");
        } else {
            tracker.removeCategory(name);
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
    // EFFECTS: Selects category of inputted name
    private void doSelectCategoryForOptions() {
        doShowAllCategories();
        System.out.println("Select category: ");
        String name = doSelectCategory();
        specificccategoryname = name;
        doSpecificCategoryOptions();
    }

    // MODIFIES:
    // EFFECTS:
    private void doSpecificCategoryOptions() {
        String command = null;
        displayScoMenu();
        command = input.next();
        command = command.toLowerCase();
        processScoCommand(command);
    }

    // EFFECTS: displays Specific Category Options Menu
    private void displayScoMenu() {
        System.out.println("Category: " + specificccategoryname + " has budget of $"
                + tracker.showCategoryBudget(specificccategoryname));
        System.out.println("Category: " + specificccategoryname + " has budget notification of $"
                + tracker.showCategoryBudgetNotifcation(specificccategoryname));
        System.out.println("\nSelect from:");
        System.out.println("\tb -> Set Category Budget ");
        System.out.println("\tn -> Set Category Budget Notification");
        System.out.println("\tm -> Main Menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processScoCommand(String command) {
        if (command.equals("b")) {
            doSetCategoryBudget();
        } else if (command.equals("n")) {
            doSetCategoryBudgetNotification();
        } else if (command.equals("m")) {
            System.out.println("");
        } else {
            System.out.println("Selection not valid, returning back to Main Menu");
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to set a specific category's budget and then changes it to the double input
    private void doSetCategoryBudget() {
        System.out.println("Input Budget Amount: ");
        Double amount = input.nextDouble();
        tracker.setCategoryBudget(specificccategoryname, amount);
        System.out.println("Set Category: " + specificccategoryname + " budget to $" + amount);
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to set a specific category's budget notification and then changes it to the double input
    private void doSetCategoryBudgetNotification() {
        System.out.println("Input Budget Notification Amount: ");
        Double amount = input.nextDouble();
        tracker.setCategoryBudgetNotification(specificccategoryname, amount);
        System.out.println("Set Category: " + specificccategoryname + " budget notification to $" + amount);
    }
}





