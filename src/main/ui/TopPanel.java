package ui;

import model.Category;
import model.Expense;
import model.Tracker;
import sun.invoke.empty.Empty;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import persistence.JsonReader;
import persistence.JsonWriter;


// Creates the Panel above table
public class TopPanel extends JPanel {
    JLabel expenseTrackerLabel = new JLabel("Expense Tracker");
    JButton saveButton;
    JButton loadButton;
    JLabel budgetLabel;
    JLabel notificationLabel;
    JLabel spentLabel;
    JLabel amountLeftLabel;
    JComboBox categoryComboBox = new JComboBox();
    JButton settingsButton = new JButton("Settings");
    Font buttonFont = new Font("Serif", Font.PLAIN, 18);
    Tracker tracker;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tracker.json";


    // EFFECTS: Initializes the panel
    public TopPanel(Tracker tracker) {
        this.tracker = tracker;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setLayout(new BorderLayout());
        JPanel nameSaveLoad = createNameSaveLoad();
        add(nameSaveLoad,BorderLayout.NORTH);
        JPanel topLabelsPanel = createNamesPanel();
        add(topLabelsPanel,BorderLayout.CENTER);
        JPanel settingsPanel = createSettingsPanel();
        add(settingsPanel,BorderLayout.SOUTH);

        JButton button = makeTestButton();
        add(button,BorderLayout.EAST);
    }

    // EFFECTS: creates Expense Tracker Label, and save/load buttons
    public JPanel createNameSaveLoad() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        Font headerFont = new Font("Serif", Font.BOLD, 30);
        expenseTrackerLabel.setFont(headerFont);
        expenseTrackerLabel.setBorder(new EmptyBorder(5, 10, 0, 200));
        panel.add(expenseTrackerLabel);
        panel.add(Box.createHorizontalGlue());
        saveButton = createSaveButton();
        loadButton = createLoadButton();
        saveButton.setFont(buttonFont);
        loadButton.setFont(buttonFont);
        saveButton.setPreferredSize(new Dimension(300,20));
        loadButton.setPreferredSize(new Dimension(300,20));
        panel.add(saveButton);
        panel.add(loadButton);
        panel.setBorder(new EmptyBorder(2,5,10,5));
        return panel;
    }

    // EFFECTS: creates labels
    public JPanel createNamesPanel() {
        ArrayList<JLabel> labels = new ArrayList<>();
        createLabels();
        labels.add(budgetLabel);
        labels.add(notificationLabel);
        labels.add(spentLabel);
        labels.add(amountLeftLabel);
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        editLabels(labels);
        panel.add(Box.createHorizontalGlue());
        for (JLabel label : labels) {
            panel.add(label);
        }
        LineBorder panelBorder = new LineBorder(Color.BLACK,2,true);
        panel.setBorder(panelBorder);

        return panel;
    }

    // EFFECTS: creates labels
    public void createLabels() {
        budgetLabel = new JLabel("Budget: $" + tracker.getTotalBudget());
        notificationLabel = new JLabel("Notification: $" + tracker.getBudgetNotification());
        spentLabel = new JLabel("Spent: $" + tracker.getTotalSpent());
        amountLeftLabel = new JLabel("Amount Left: $" + tracker.getAmountLeftInBudget());
    }


    // EFFECTS: sets font and border of labels
    public void editLabels(ArrayList<JLabel> labels) {
        EmptyBorder centerLabelBorder = new EmptyBorder(0,20,0,20);
        Font labelFont = new Font("Serif", Font.PLAIN, 20);
        for (JLabel label : labels) {
            label.setBorder(centerLabelBorder);
            label.setFont(labelFont);
        }
    }

    // EFFECTS: Creates combo box of categories and settings button
    public JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        settingsButton.setFont(buttonFont);
        settingsButton.setPreferredSize(new Dimension(300,20));
        categoryComboBox = createCategoryComboBox();
        panel.add(categoryComboBox);
        panel.add(Box.createHorizontalGlue());
        panel.add(settingsButton);
        panel.setBorder(new EmptyBorder(10,5,10,5));
        return panel;
    }

    // EFFECTS: Creates Category ComboBox
    public JComboBox createCategoryComboBox() {
        ArrayList<String> categoriesArray = new ArrayList<>();
        for (Category category : tracker.getCategories()) {
            categoriesArray.add(category.getCategoryName());
        }

        Object[] categoryStrings = {};
        categoryStrings = categoriesArray.toArray();
        JComboBox categoryBox = new JComboBox<>(categoryStrings);
        categoryBox.setPreferredSize(new Dimension(200,20));
        categoryBox.setFont(buttonFont);
        return categoryBox;
    }

    public void updateLabels() {
        budgetLabel.setText("Budget: $" + tracker.getTotalBudget());
        notificationLabel.setText("Notification: $" + tracker.getBudgetNotification());
        spentLabel.setText("Spent: $" + tracker.getTotalSpent());
        amountLeftLabel.setText("Amount Left: $" + tracker.getAmountLeftInBudget());
    }

    public JButton createSaveButton() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(tracker);
                    jsonWriter.close();
                    System.out.println("Saved Tracker to " + JSON_STORE);
                } catch (FileNotFoundException exception) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        });
        return saveButton;
    }

    public JButton createLoadButton() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tracker = jsonReader.read();
                    System.out.println("Loaded tracker from " + JSON_STORE);
                } catch (IOException exception) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }

                updateLabels();
            }
        });
        return loadButton;

    }

    public JButton makeTestButton() {
        JButton button = new JButton("test");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // adds to tracker
                updateLabels();

                // testing
                for (Expense expense : tracker.getExpenses()) {
                    System.out.println(expense.getCategoryName());
                    System.out.println(expense.getItemName());
                    System.out.println(expense.getAmount());
                }
            }
        });
        return button;
    }


}

