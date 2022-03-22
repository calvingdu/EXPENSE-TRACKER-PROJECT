package ui;

import model.Category;
import model.Tracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BottomPanel extends JPanel {
    JComboBox categorySelectorBox;
    JTextField itemNameTextField;
    JTextField amountTextField;
    JButton addExpenseButton;
    JLabel nameLabel;
    JLabel amountLabel;
    Font buttonFont = new Font("Serif", Font.PLAIN, 18);
    // testing
    Tracker tracker = new Tracker();

    public BottomPanel() {
        // testing
        tracker.newCategory("Groceries",100);
        tracker.newCategory("Bruh",100);
        tracker.setTotalBudget(100);
        tracker.setBudgetNotification(10);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        categorySelectorBox = createCategorySelectorBox();
        itemNameTextField = createItemNameTextField();
        amountTextField = createAmountTextField();
        addExpenseButton = createAddExpenseButton();
        createLabels();

        add(categorySelectorBox);
        add(nameLabel);
        add(itemNameTextField);
        add(amountLabel);
        add(amountTextField);
        add(addExpenseButton);

        setBorder(new EmptyBorder(10,5,10,5));
    }

    public JComboBox createCategorySelectorBox() {
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

    public JTextField createItemNameTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(40,20));
        return textField;
    }

    public JTextField createAmountTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(20,20));
        return textField;
    }

    public JButton createAddExpenseButton() {
        JButton addExpenseButton = new JButton("ADD EXPENSE");
        addExpenseButton.setFont(buttonFont);
        addExpenseButton.setPreferredSize(new Dimension(200,20));

        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object category = categorySelectorBox.getSelectedItem();
                String stringCategory = category.toString();
                String name = itemNameTextField.getText();
                String amount = amountTextField.getText();
                Double amountDouble = Double.parseDouble(amount);

                //Clear the fields
                itemNameTextField.setText("");
                amountTextField.setText("");

                // adds to tracker
                tracker.addExpense(stringCategory,name,amountDouble);
                // testing
                System.out.println(stringCategory + name + amountDouble);
                System.out.println(tracker.getExpenses());
                System.out.println(tracker.getExpenses().get(0).getCategoryName());
                System.out.println(tracker.getExpenses().get(0).getItemName());
                System.out.println(tracker.getExpenses().get(0).getAmount());
                System.out.println(tracker.getExpenses().get(1).getCategoryName());
            }
        });
        return addExpenseButton;
    }

    public void createLabels() {
        nameLabel = new JLabel("Item Name:");
        amountLabel = new JLabel("Amount:");
        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(nameLabel);
        labels.add(amountLabel);

        for (JLabel label : labels) {
            label.setFont(buttonFont);
            label.setBorder(new EmptyBorder(0,15,0,5));
            label.setMinimumSize(new Dimension(70,20));
        }

        nameLabel.setMinimumSize(new Dimension(100,20));


    }
}
