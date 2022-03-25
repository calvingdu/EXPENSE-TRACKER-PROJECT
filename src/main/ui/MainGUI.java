package ui;


import model.Tracker;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

// Creates GUI with table and panels
public class MainGUI {
    Tracker tracker = new Tracker();
    JFrame frame = new JFrame("Expense Tracker");
    TopPanel topPanel;
    BottomPanel bottomPanel;
    TablePanel tablePanel;


    // EFFECTS: Initializes frame
    public MainGUI() {
        tablePanel = new TablePanel(tracker);
        topPanel = new TopPanel(this);
        bottomPanel = new BottomPanel(this);

        frame.setLayout(new BorderLayout());
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(tablePanel,BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);


        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: updates the labels and table to represent data from the tracker
    public void update() {
        topPanel.updateLabels();
        tablePanel.updateTrackerTable();
    }

    // EFFECTS: updates the ComboBoxes to represent the categories in tracker
    public void updateCategoryBox() {
        topPanel.updateCategoryComboBox();
        bottomPanel.updateCategoryComboBox();
    }

    // EFFECTS: filters table to a selection in the top panel
    public void filterTable(String input) {
        TableModel model = tablePanel.getModel();

        String filterString = input;

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        tablePanel.getTable().setRowSorter(sorter);

        if (filterString.equals("Show All")) {
            RowFilter<TableModel, Object> rf  = RowFilter.regexFilter("", 0);
            sorter.setRowFilter(rf);

        } else {
            RowFilter<TableModel, Object> rf  = RowFilter.regexFilter(filterString, 0);
            sorter.setRowFilter(rf);
        }

    }

    // EFFECTS: Sets trackers in all panels, so they're all the same
    public void setTrackers(Tracker tracker) {
        this.tracker = tracker;
        tablePanel.setTracker(tracker);
        bottomPanel.setTracker(tracker);
    }

    // EFFECTS: Starts main GUI
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainGUI();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public Tracker getTracker() {
        return tracker;
    }


}
