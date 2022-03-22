package ui;

import model.Expense;
import model.Tracker;
import sun.invoke.empty.Empty;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Creates the Panel above table
public class TopPanel extends JPanel {
    JLabel expenseTrackerLabel = new JLabel("Expense Tracker");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JLabel budgetLabel;
    JLabel notificationLabel;
    JLabel spentLabel;
    JLabel amountLeftLabel;
    JComboBox categoryComboBox = new JComboBox();
    JButton settingsButton = new JButton("Settings");
    Font buttonFont = new Font("Serif", Font.PLAIN, 18);
    Tracker tracker;


    // EFFECTS: Initializes the panel
    public TopPanel(Tracker tracker) {
        // testing
        this.tracker = tracker;

        setLayout(new BorderLayout());
        JPanel nameSaveLoad = createNameSaveLoad();
        add(nameSaveLoad,BorderLayout.NORTH);
        JPanel topLabelsPanel = createNamesPanel();
        add(topLabelsPanel,BorderLayout.CENTER);
        JPanel settingsPanel = createSettingsPanel();
        add(settingsPanel,BorderLayout.SOUTH);
        JButton button = makeTestButton();
        add(button);
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
        String[] categoryStrings = { "Groceries", "Textbooks"};
        JComboBox categoryBox = new JComboBox<>(categoryStrings);
        categoryBox.setPreferredSize(new Dimension(40,20));
        categoryBox.setFont(buttonFont);
        return categoryBox;
    }

    public JButton makeTestButton() {
        JButton button = new JButton("test");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // adds to tracker
                tracker.addExpense("Groceries", "carrots", 500);

                // testing
                for (Expense expense : tracker.getExpenses()) {
                    System.out.println(expense.getCategoryName());
                }
            }
        });
        return button;
    }
}

