package ui;


import model.Event;
import model.EventLog;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;


// Creates GUI with table and panels
public class MainGUI {
    Tracker tracker = new Tracker();
    JFrame frame = new JFrame("Expense Tracker");
    TopPanel topPanel;
    BottomPanel bottomPanel;
    TablePanel tablePanel;
    JFrame initialFrame;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tracker.json";

    // EFFECTS: Initializes mainGUI
    public MainGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        tablePanel = new TablePanel(tracker);
        topPanel = new TopPanel(this);
        bottomPanel = new BottomPanel(this);


        frame.setLayout(new BorderLayout());
        frame.add(topPanel,BorderLayout.NORTH);
        frame.add(tablePanel,BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);


        // Close Window
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });

        init();
    }

    // EFFECTS: Brings up initializing frame
    public void init() {
        initialFrame = new InitialFrame(this);
        initialFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        initialFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                EventLog el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println(next.toString());
                }
                initialFrame.dispose();
                frame.pack();
                frame.setVisible(true);
            }
        });

        initialFrame.pack();
        initialFrame.setLocationRelativeTo(null);
        initialFrame.setVisible(true);
    }

    // MODIFIES: topPanel, tablePanel
    // EFFECTS: updates the labels and table to represent data from the tracker
    public void update() {
        topPanel.updateLabels();
        tablePanel.updateTrackerTable();
    }

    // MODIFIES: topPanel, bottomPanel
    // EFFECTS: updates the ComboBoxes to represent the categories in tracker
    public void updateCategoryBox() {
        topPanel.updateCategoryComboBox();
        bottomPanel.updateCategoryComboBox();
    }

    // MODIFIES: tablePanel
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

    // MODIFIES: tablePanel, bottomPanel
    // EFFECTS: Sets trackers in all panels, so they're all the same
    public void setTrackers(Tracker tracker) {
        this.tracker = tracker;
        topPanel.setTracker(tracker);
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

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }
}
