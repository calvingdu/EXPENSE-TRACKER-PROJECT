package ui;

import model.Event;
import model.EventLog;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

// JFrame that opens at startup of MAINGUI
public class InitialFrame extends JFrame {
    MainGUI main;
    Tracker tracker;
    JFrame frame;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tracker.json";

    JPanel initialPanel;

    JPanel labelPanel;
    JButton newTrackerButton;
    JButton loadButton;

    JPanel newTrackerPanel;

    JTextField budgetTextField;
    JTextField notificationTextField;
    JTextField categoryTextField;

    Font font = new Font("Serif", Font.PLAIN, 18);

    public InitialFrame(MainGUI main) {
        this.main = main;
        this.tracker = main.getTracker();
        this.frame = main.getFrame();
        this.jsonReader = main.getJsonReader();


        initialPanel = new JPanel();
        initialPanel.setLayout(new BorderLayout());

        labelPanel = createLabelPanel();

        loadButton = createLoadButton();
        newTrackerButton = createNewTrackerButton();

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(loadButton);
        buttons.add(newTrackerButton);
        editButtons(buttons);

        initialPanel.add(labelPanel,BorderLayout.CENTER);
        initialPanel.add(loadButton,BorderLayout.WEST);
        initialPanel.add(newTrackerButton,BorderLayout.EAST);

        add(initialPanel);
    }

    // EFFECTS: Creates a panel with various labels on it
    public JPanel createLabelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel setupLabel = new JLabel("Expense Tracker");
        JLabel nameLabel = new JLabel("By: Calvin Du");
        JLabel emailLabel = new JLabel("calvingdu@gmail.com");

        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(setupLabel);
        labels.add(nameLabel);
        labels.add(emailLabel);

        for (JLabel label : labels) {
            label.setFont(new Font("Serif", Font.PLAIN, 20));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setPreferredSize(new Dimension(400,50));
        }

        setupLabel.setFont(new Font("Serif", Font.BOLD, 35));
        panel.add(setupLabel, BorderLayout.NORTH);
        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(emailLabel, BorderLayout.SOUTH);

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: Creates a button that loads data from JSON file and exits initializing frame
    public JButton createLoadButton() {
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tracker = jsonReader.read();
                } catch (IOException exception) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                }

                finishInitial();
            }
        });
        return loadButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates a button that starts the setup for a new tracker
    public JButton createNewTrackerButton() {
        JButton button = new JButton("Set up New Tracker");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(initialPanel);
                repaint();

                newTrackerPanel = newTrackerPanel();
                add(newTrackerPanel);
                pack();
                setLocationRelativeTo(null);
            }
        });
        return button;
    }

    // EFFECTS: Creates a panel where setup of tracker happens
    public JPanel newTrackerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: Creates a panel with labels and text fields for input
    public JPanel createInputPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel topLabel = new JLabel("Expense Tracker");
        editLabel(topLabel);
        topLabel.setBorder(new EmptyBorder(25,5,25,5));
        topLabel.setPreferredSize(new Dimension(100, 30));
        topLabel.setFont(new Font("Serif", Font.BOLD, 25));
        panel.add(topLabel);

        JPanel budgetPanel = createBudgetPanel();
        JPanel notificationPanel = createNotificationPanel();
        JPanel categoryPanel = createCategoryPanel();

        ArrayList<JPanel> panels = new ArrayList<>();
        panels.add(budgetPanel);
        panels.add(notificationPanel);
        panels.add(categoryPanel);

        for (JPanel p : panels) {
            panel.add(p);
            panel.setPreferredSize(new Dimension(400, 200));
            panel.setBorder(new EmptyBorder(10,5,10,5));
        }

        return panel;
    }

    // EFFECTS: Creates a Panel with text field to set budget
    public JPanel createBudgetPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("Budget:");
        editLabel(label);
        budgetTextField = new JTextField();
        editTextField(budgetTextField);

        panel.add(label);
        panel.add(budgetTextField);

        return panel;
    }

    // EFFECTS: Creates a Panel with text field to set notification amount
    public JPanel createNotificationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("Notify At:");
        editLabel(label);
        notificationTextField = new JTextField();
        editTextField(notificationTextField);

        panel.add(label);
        panel.add(notificationTextField);

        return panel;
    }

    // EFFECTS: Creates a Panel with text field to make a new category
    public JPanel createCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("First Category:");
        editLabel(label);
        categoryTextField = new JTextField();
        editTextField(categoryTextField);

        panel.add(label);
        panel.add(categoryTextField);

        return panel;
    }

    // EFFECTS: Edits given label for a specific font/size
    public void editLabel(JLabel label) {
        label.setFont(font);
        label.setPreferredSize(new Dimension(150,20));
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setBorder(new EmptyBorder(5,0,0,10));
    }

    // EFFECTS: Edits given text field
    public void editTextField(JTextField textField) {
        textField.setSize(new Dimension(300, 20));
    }


        // EFFECTS: Creates a Panel that contains the create expense tracker button
    public JPanel createButtonPanel() {
        JPanel panel = new JPanel();

        JButton button = new JButton("Create Expense Tracker");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String budget = budgetTextField.getText();
                String notification = notificationTextField.getText();
                String category = categoryTextField.getText();
                Double amountBudget = Double.parseDouble(budget);
                Double amountNotification = Double.parseDouble(notification);

                //Clear the fields
                budgetTextField.setText("");
                notificationTextField.setText("");
                categoryTextField.setText("");

                // update tracker
                tracker.setTotalBudget(amountBudget);
                tracker.setBudgetNotification(amountNotification);
                tracker.newCategory(category, 100);

                finishInitial();
            }
        });
        button.setFont(new Font("Serif", Font.PLAIN, 18));

        panel.add(button);

        return panel;
    }

    // MODIFIES: THIS
    // EFFECTS: Ends the initial frame and shows main frame
    public void finishInitial() {
        main.setTrackers(tracker);
        main.update();
        main.updateCategoryBox();

        dispose();
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Edits a list of buttons for consistent syntax
    public void editButtons(ArrayList<JButton> buttons) {
        for (JButton button : buttons) {
            button.setFont(font);
            button.setPreferredSize(new Dimension(300,20));
        }
    }



}
