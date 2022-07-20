package ui;

import model.Category;
import model.Event;
import model.EventLog;
import model.Tracker;

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
    JButton filterButton = new JButton();
    JButton newCategoryButton = new JButton();
    JButton newBudgetButton = new JButton();
    JButton newNotificationButton = new JButton();
    Font buttonFont = new Font("Serif", Font.PLAIN, 18);
    Tracker tracker;
    MainGUI main;
    JFrame frame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tracker.json";


    // EFFECTS: Initializes the panel
    public TopPanel(MainGUI main) {
        this.main = main;
        frame = main.getFrame();
        this.tracker = main.getTracker();
        jsonWriter = main.getJsonWriter();
        jsonReader = main.getJsonReader();

        setLayout(new BorderLayout());
        JPanel nameSaveLoad = createNameSaveLoad();
        add(nameSaveLoad,BorderLayout.NORTH);
        JPanel topLabelsPanel = createNamesPanel();
        add(topLabelsPanel,BorderLayout.CENTER);
        JPanel settingsPanel = createSettingsPanel();
        add(settingsPanel,BorderLayout.SOUTH);
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

    // EFFECTS: creates a button used to save data
    public JButton createSaveButton() {
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(tracker);
                    jsonWriter.close();
                } catch (FileNotFoundException exception) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
                JOptionPane.showMessageDialog(frame,
                        "Saved");
            }
        });
        saveButton.setBorder(BorderFactory.createLineBorder(Color.black));
        return saveButton;
    }

    // EFFECTS: creates a button used to load data
    public JButton createLoadButton() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tracker = jsonReader.read();
                } catch (IOException exception) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }

                main.setTrackers(tracker);
                main.update();
                main.updateCategoryBox();
                JOptionPane.showMessageDialog(frame,
                        "Loaded");
            }
        });
        loadButton.setBorder(BorderFactory.createLineBorder(Color.black));
        return loadButton;
    }

    // MODIFIES: this
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

    // EFFECTS: updates labels with tracker's data
    public void updateLabels() {
        budgetLabel.setText("Budget: $" + tracker.getTotalBudget());
        notificationLabel.setText("Notification: $" + tracker.getBudgetNotification());
        spentLabel.setText("Spent: $" + tracker.getTotalSpent());
        amountLeftLabel.setText("Amount Left: $" + tracker.getAmountLeftInBudget());
    }


    // EFFECTS: Creates combo box of categories and settings button
    public JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        categoryComboBox = createCategoryComboBox();
        panel.add(categoryComboBox);
        filterButton = createFilterButton();
        panel.add(filterButton);
        panel.add(Box.createHorizontalGlue());

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(newBudgetButton = createNewBudgetButton());
        buttons.add(newNotificationButton = createNewNotifButton());
        buttons.add(newCategoryButton = createNewCategoryButton());

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(new Dimension(175,20));
            button.setBorder(BorderFactory.createLineBorder(Color.black));
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(15,20)));
        }
        panel.setBorder(new EmptyBorder(10,5,10,5));

        return panel;
    }


    // EFFECTS: Creates Category ComboBox
    public JComboBox createCategoryComboBox() {
        JComboBox categoryBox = new JComboBox();
        DefaultComboBoxModel model = (DefaultComboBoxModel) categoryBox.getModel();
        model.removeAllElements();

        model.addElement("Show All");
        for (Category category : tracker.getCategories()) {
            model.addElement(category.getCategoryName());
        }

        categoryBox.setModel(model);

        categoryBox.setPreferredSize(new Dimension(200,20));
        categoryBox.setFont(buttonFont);
        return categoryBox;
    }

    // EFFECTS: Updates CategoryComboBox to have categories from tracker model
    public void updateCategoryComboBox() {
        categoryComboBox.removeAllItems();

        categoryComboBox.addItem("Show All");
        for (Category category : tracker.getCategories()) {
            categoryComboBox.addItem(category.getCategoryName());
        }

        categoryComboBox.setPreferredSize(new Dimension(200,20));
        categoryComboBox.setFont(buttonFont);
    }

    // EFFECTS: creates button that filters table
    public JButton createFilterButton() {
        JButton button = new JButton("Filter");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.filterTable(categoryComboBox.getSelectedItem().toString());
            }
        });

        button.setFont(buttonFont);
        button.setPreferredSize(new Dimension(150,20));
        button.setBorder(BorderFactory.createLineBorder(Color.black));
        return button;
    }



    // EFFECTS: creates button to make a new category
    public JButton createNewCategoryButton() {
        JButton button = new JButton("New Category");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("New Category Name:");
                tracker.newCategory(inputValue, 100);
                main.updateCategoryBox();
                }
        });
        return button;
    }

    // EFFECTS: creates a button to set tracker's total budget
    public JButton createNewBudgetButton() {
        JButton button = new JButton("Set Budget");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Set Budget:");
                Double amountDouble = Double.parseDouble(inputValue);
                tracker.setTotalBudget(amountDouble);
                main.update();
            }
        });
        return button;
    }

    // EFFECTS: creates a button to set the tracker's notification amount
    public JButton createNewNotifButton() {
        JButton button = new JButton("Set Notification");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Set Notification Amount:");
                Double amountDouble = Double.parseDouble(inputValue);
                tracker.setBudgetNotification(amountDouble);
                main.update();
            }
        });
        return button;
    }

    // Modifies: this
    // EFFECTS: sets tracker
    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }
}

