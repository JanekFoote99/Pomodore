package main.Pomodore.TODOList;

import main.Pomodore.XMLParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TODOList
{
    private DefaultTableModel tableModel;
    private JTable todoTable;

    // TODOList Constructor for an exmpty list. Gets called when the XMLParser
    // throws an Exception when reading the config
    public TODOList(JPanel panel)
    {
        setupList(panel);
    }

    // Constructor with read Todos
    public TODOList(JPanel panel, ArrayList<TODOItem> items)
    {
        setupList(panel);

        for (TODOItem item : items)
        {
            addItem(item.getTextField().getText().trim());
        }
    }

    private void setupList(JPanel panel)
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
                } else if (column == 2)
                {
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
    }

    public void addItem(String text)
    {
        TODOItem item = new TODOItem(text);

        item.getDeleteButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                deleteItem(item);
            }
        });

        item.getCheckBox().addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (item.getCheckBox().isSelected())
                {
                    item.getTextField().setBackground(Color.GREEN);
                } else
                {
                    item.getTextField().setBackground(Color.WHITE);
                }
                // update JTable. Else the Color change is delayed upon the next Click
                todoTable.repaint();
            }
        });

        tableModel.addRow(new Object[]{item, item, item});
    }

    // Problem: if two Entries have the same name, the first one detected in the Loop will be deleted
    // This is considered a bug within a very niche area as it is impractical to have the same
    // Entry twice in a Todolist
    public void deleteItem(TODOItem item)
    {
        String text = item.getTextField().getText();
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
        {
            String textFieldString = ((TODOItem) todoTable.getValueAt(i, 0)).getTextField().getText();
            if (textFieldString.equals(text))
            {
                // Without the underlying if Statement, the JTable stays in editing Mode.
                // As long as the JTable is in editing Mode, the Values in it won't get updated.
                // That results in an IndexOutOfBoundsException, when trying to delete
                // two elements consecutively on the same Row.
                // e.g. you delete Task 2 in Row 2. Task 3 moves from Row 3 to Row 2, making it unable to be deleted
                if (todoTable.isEditing() && todoTable.getEditingRow() == i)
                {
                    todoTable.getCellEditor().stopCellEditing();
                }
                tableModel.removeRow(i);
                break;
            }
        }
    }

    public ArrayList<TODOItem> getTodos()
    {
        ArrayList<TODOItem> todos = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++)
        {
            todos.add((TODOItem)tableModel.getValueAt(i, 0));
        }

        return todos;
    }
}