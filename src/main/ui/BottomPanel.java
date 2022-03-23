package ui;

import model.Category;
import model.Tracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Creates the input panel below table
public class BottomPanel extends JPanel {
    JComboBox categoryComboBox;
    JTextField itemNameTextField;
    JTextField amountTextField;
    JButton addExpenseButton;
    JLabel nameLabel;
    JLabel amountLabel;
    Font buttonFont = new Font("Serif", Font.PLAIN, 18);
    Tracker tracker;
    JFrame frame;
    MainGUI main;

    // EFFECTS: creates a new bottom panel with category box gotten through tracker
    public BottomPanel(MainGUI main) {
        setTracker(main.getTracker());

        this.main = main;

        this.frame = main.getFrame();

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        createCategorySelectorBox();
        itemNameTextField = createItemNameTextField();
        amountTextField = createAmountTextField();
        addExpenseButton = createAddExpenseButton();
        createLabels();

        add(categoryComboBox);
        add(nameLabel);
        add(itemNameTextField);
        add(amountLabel);
        add(amountTextField);
        add(addExpenseButton);

        setPreferredSize(new Dimension(1500,50));

        setBorder(new EmptyBorder(10,5,10,5));
    }

    // EFFECTS: Creates the ComboBox used to choose a category
    public void createCategorySelectorBox() {
        JComboBox categoryBox = new JComboBox();
        DefaultComboBoxModel model = (DefaultComboBoxModel) categoryBox.getModel();
        model.removeAllElements();

        for (Category category : tracker.getCategories()) {
            model.addElement(category.getCategoryName());
        }

        categoryBox.setModel(model);

        categoryBox.setPreferredSize(new Dimension(200,20));
        categoryBox.setFont(buttonFont);
        categoryComboBox = categoryBox;
    }

    // EFFECTS: Updates CategoryComboBox to have categories from tracker model
    public void updateCategoryComboBox() {
        categoryComboBox.removeAllItems();

        for (Category category : tracker.getCategories()) {
            categoryComboBox.addItem(category.getCategoryName());
        }

        categoryComboBox.setPreferredSize(new Dimension(200,20));
        categoryComboBox.setFont(buttonFont);
    }

    // EFFECTS: Creates the text field used to take in the item name
    public JTextField createItemNameTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(40,20));
        return textField;
    }

    // EFFECTS: Creates the text field used to take in the amount for the expense
    public JTextField createAmountTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(20,20));
        return textField;
    }

    // EFFECTS: Creates the button which adds an expense
    public JButton createAddExpenseButton() {
        JButton addExpenseButton = new JButton("ADD EXPENSE");
        addExpenseButton.setFont(buttonFont);
        addExpenseButton.setPreferredSize(new Dimension(200,20));

        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object category = categoryComboBox.getSelectedItem();
                String stringCategory = category.toString();
                String name = itemNameTextField.getText();
                String amount = amountTextField.getText();
                Double amountDouble = Double.parseDouble(amount);

                //Clear the fields
                itemNameTextField.setText("");
                amountTextField.setText("");

                // adds to tracker
                tracker.addExpense(stringCategory,name,amountDouble);

                // checks for budget notification or over budget
                checkNotifications();
                main.update();

                System.out.println(tracker.notifyNearBudget());
            }
        });

        addExpenseButton.setBorder(BorderFactory.createLineBorder(Color.black));
        return addExpenseButton;
    }

    // EFFECTS: creates Labels to identify text fields
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

    // EFFECTS: sets Tracker
    public void setTracker(Tracker newTracker) {
        tracker = newTracker;
    }

    // EFFECTS: displays popup if tracker goes below notification or over budget
    public void checkNotifications() {
        ImageIcon image = new ImageIcon("./data/jade.jpeg");
        Image originalImage = image.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resizedImage);

        if (tracker.notifyNearBudget()) {
            JOptionPane.showMessageDialog(frame,
                    "Warning: You have $" + tracker.getAmountLeftInBudget() + " left in your budget");
        }
        if (tracker.notifyOverBudget()) {
            JOptionPane.showMessageDialog(frame,
                    "You are over budget by $" + tracker.getAmountOverBudget() + "!",
                    "Over Budget",
                    JOptionPane.INFORMATION_MESSAGE,
                    imageIcon);
        }
    }
}
