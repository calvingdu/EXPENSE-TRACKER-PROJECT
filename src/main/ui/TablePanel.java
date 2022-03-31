package ui;

import model.Expense;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// JPanel which includes the ScrollPanel and the table within ScrollPanel
public class TablePanel extends JPanel {
    Tracker tracker;

    JScrollPane tablePane;
    DefaultTableModel model;
    JTable table;

    public TablePanel(Tracker tracker) {
        this.tracker = tracker;

        tablePane = makeTableScrollPane();
        add(tablePane);
    }


    // EFFECTS: Makes the table/scrollpane
    private JScrollPane makeTableScrollPane() {
        String[] columnNames = {"Category", "Item", "Amount"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        updateTrackerTable();

        table = new JTable(model);
        editTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    // MODIFIES: this
    // EFFECTS: Updates table to be up to date with tracker
    public void updateTrackerTable() {
        model.setRowCount(0);
        for (Expense expense : tracker.getExpenses()) {
            model.addRow(
                    new Object[]{expense.getCategoryName(),
                            expense.getItemName(),
                            "$" + expense.getAmount()}
            );
        }
    }

    // MODIFIES: this
    // EFFECTS: Changes table look
    private void editTable(JTable table) {
        Font headerFont = new Font("Serif", Font.BOLD, 24);
        Font tableFont = new Font("Serif", Font.PLAIN, 18);
        table.setPreferredScrollableViewportSize(new Dimension(1400, 600));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setFont(headerFont);
        header.setBorder(BorderFactory.createLineBorder(Color.black));;
        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        table.setFont(tableFont);
    }

    // MODIFIES: tracker
    // EFFECTS: Sets tracker to be tracker used in mainGUI
    public void setTracker(Tracker newTracker) {
        tracker = newTracker;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }



}
