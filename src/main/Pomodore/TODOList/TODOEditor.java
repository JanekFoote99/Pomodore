package main.Pomodore.TODOList;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

class TODOEditor extends AbstractCellEditor implements TableCellEditor
{
    private TODOItem currentItem;
    private int currentColumn;

    @Override
    public Object getCellEditorValue() {
        return currentItem;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof TODOItem) {
            currentItem = (TODOItem) value;
            currentColumn = column;
            if (column == 0) {
                currentItem.getTextField().requestFocus();
                return currentItem.getTextField();
            } else if (column == 1) {
                currentItem.getCheckBox().requestFocus();
                return currentItem.getCheckBox();
            }
        }
        return null;
    }
}