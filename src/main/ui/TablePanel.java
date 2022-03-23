package ui;

import model.Expense;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePanel extends JPanel {
    // testing
    Tracker tracker;

    JScrollPane tablePane;
    DefaultTableModel model;
    JTable table;


    public TablePanel(Tracker tracker) {
        this.tracker = tracker;

        tablePane = makeTableScrollPane();
        add(tablePane);

        // testing
        JButton button = makeTestButton();
        add(button);
    }


    // EFFECTS: Makes the table
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

    // EFFECTS: Changes table to settings
    private void editTable(JTable table) {
        Font headerFont = new Font("Serif", Font.BOLD, 24);
        Font tableFont = new Font("Serif", Font.PLAIN, 18);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(Color.black);
        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setFont(headerFont);
        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        table.setFont(tableFont);
    }

    public JButton makeTestButton() {
        JButton button = new JButton("test");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // adds to tracker
                updateTrackerTable();

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
