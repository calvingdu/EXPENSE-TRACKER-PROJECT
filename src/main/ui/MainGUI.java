package ui;


import model.Tracker;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Creates GUI with table and panels
public class MainGUI {
    Tracker tracker = new Tracker();
    JFrame frame = new JFrame("Expense Tracker");
    JPanel topPanel = new TopPanel(tracker);
    JPanel bottomPanel = new BottomPanel();
    JPanel table = new TablePanel(tracker);

    // EFFECTS: Initializes frame
    public MainGUI() {
        // testing
        tracker.newCategory("Groceries",100);
        tracker.newCategory("Food",100);
        tracker.setTotalBudget(100);
        tracker.setBudgetNotification(10);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(table,BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);


        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Starts main GUI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }
}
