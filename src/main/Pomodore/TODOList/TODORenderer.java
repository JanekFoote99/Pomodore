package main.Pomodore.TODOList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TODORenderer extends DefaultTableCellRenderer
{
    private JTextField textField;
    private JCheckBox checkBox;

    public TODORenderer()
    {
        setLayout(new BorderLayout());
        textField = new JTextField();
        checkBox = new JCheckBox();
        add(textField, BorderLayout.CENTER);
        add(checkBox, BorderLayout.EAST);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof TODOItem) {
            TODOItem item = (TODOItem) value;
            if (column == 0) {
                return item.getTextField();
            } else if (column == 1) {
                item.setCheckbox(isSelected);
                return item.getCheckBox();
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    public JTextField getTextField()
    {
        return textField;
    }

    public JCheckBox getCheckBox()
    {
        return checkBox;
    }
}