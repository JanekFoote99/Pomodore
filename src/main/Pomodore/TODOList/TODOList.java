package main.Pomodore.TODOList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TODOList
{
    private DefaultTableModel tableModel;
    private JTable todoTable;

    public TODOList(JPanel panel)
    {
        tableModel = new DefaultTableModel(new Object[]{"Tasks", "Checkbox", "Delete"}, 0);
        todoTable = new JTable(tableModel)
        {
            @Override
            public Class<?> getColumnClass(int column)
            {
                if (column == 1)
                {
                    return Boolean.class;
                }
                else if(column == 2){
                    return JButton.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return true;
            }
        };

        todoTable.setRowHeight(26);

        // Disable the Header Names of the DefaultTableModel
        todoTable.setTableHeader(null);

        todoTable.getColumnModel().getColumn(0).setCellRenderer(new TODORenderer());
        todoTable.getColumnModel().getColumn(1).setCellRenderer(new TODORenderer());
        todoTable.getColumnModel().getColumn(2).setCellRenderer(new TODORenderer());
        todoTable.getColumnModel().getColumn(0).setCellEditor(new TODOEditor());
        todoTable.getColumnModel().getColumn(1).setCellEditor(new TODOEditor());
        todoTable.getColumnModel().getColumn(2).setCellEditor(new TODOEditor());

        // Set fixed Width of checkbox disregarding of Window size (more space for the Item Text)
        int checkboxWidth = 50;
        todoTable.getColumnModel().getColumn(1).setPreferredWidth(checkboxWidth);
        todoTable.getColumnModel().getColumn(1).setMaxWidth(checkboxWidth);

        todoTable.getColumnModel().getColumn(2).setPreferredWidth(checkboxWidth);
        todoTable.getColumnModel().getColumn(2).setMaxWidth(checkboxWidth);

        JScrollPane scrollPane = new JScrollPane(todoTable);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addItemButton = new JButton("Add new Item");
        panel.add(addItemButton, BorderLayout.SOUTH);

        addItemButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addItem("");
            }
        });

        addItem("Task 1");
        addItem("Task 2");
        addItem("Task 3");
    }

    public void addItem(String text)
    {
        TODOItem item = new TODOItem(text);

        item.getDeleteButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = tableModel.getRowCount() - 1;
                deleteItem(index);
            }
        });

        tableModel.addRow(new Object[]{item, item, item});
    }

    public void deleteItem(int index)
    {
        tableModel.removeRow(index);
    }
}