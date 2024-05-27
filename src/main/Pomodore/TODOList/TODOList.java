package main.Pomodore.TODOList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TODOList
{
    private DefaultTableModel tableModel;
    private JTable todoTable;

    public TODOList(JPanel panel)
    {
        tableModel = new DefaultTableModel(new Object[]{"Tasks", "Checkbox"}, 0);
        todoTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 1) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        todoTable.getColumnModel().getColumn(0).setCellRenderer(new TODORenderer());
        todoTable.getColumnModel().getColumn(1).setCellRenderer(new TODORenderer());
        todoTable.getColumnModel().getColumn(0).setCellEditor(new TODOEditor());
        todoTable.getColumnModel().getColumn(1).setCellEditor(new TODOEditor());

        // Set fixed Width of checkbox disregarding of Window size (more space for the Item Text)
        /*int checkboxWidth = 50;
        todoTable.getColumnModel().getColumn(1).setPreferredWidth(checkboxWidth);
        todoTable.getColumnModel().getColumn(1).setMaxWidth(checkboxWidth);*/

        JScrollPane scrollPane = new JScrollPane(todoTable);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        addItem("Task 1");
        addItem("Task 2");
        addItem("Task 3");
    }

    public void addItem(String text) {
        tableModel.addRow(new Object[]{new TODOItem(text)});
    }
}