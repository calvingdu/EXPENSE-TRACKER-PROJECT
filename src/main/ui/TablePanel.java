package ui;

import model.Category;
import model.Expense;
import model.Tracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class TablePanel extends JPanel {
    // testing
    Tracker tracker = new Tracker();

    JScrollPane tablePane;
    DefaultTableModel model;
    JTable table;


    public TablePanel() {
        tracker.newCategory("Groceries", 100);
        tracker.newCategory("Bruh", 100);
        tracker.setTotalBudget(100);
        tracker.setBudgetNotification(10);
        tracker.addExpense("Groceries", "Carrots", 5);
        tracker.addExpense("Bruh", "Bruh", 10);

        tablePane = makeTableScrollPane();
        add(tablePane);
    }


    // EFFECTS: Makes the table
    private JScrollPane makeTableScrollPane() {

        String[] columnNames = {"Category", "Item", "Amount"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);

        for (Expense e : tracker.getExpenses()) {
            model.addRow(
                    new Object[]{e.getCategoryName(),
                            e.getItemName(),
                            "$" + e.getAmount()}
            );
        }


        editTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
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

}
