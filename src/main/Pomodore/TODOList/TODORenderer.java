package main.Pomodore.TODOList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class TODORenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (value instanceof TODOItem)
        {
            TODOItem item = (TODOItem) value;
            if (column == 0)
            {
                return item.getTextField();
            } else if (column == 1)
            {
                return item.getCheckBox();
            } else if (column == 2)
            {
                return item.getDeleteButton();
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}