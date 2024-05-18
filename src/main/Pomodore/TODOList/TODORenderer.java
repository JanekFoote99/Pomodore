package main.Pomodore.TODOList;

import javax.swing.*;
import java.awt.*;

class TODORenderer extends JPanel implements ListCellRenderer<TODOItem>
{
    private JLabel label;
    private JCheckBox checkBox;

    public TODORenderer()
    {
        setLayout(new BorderLayout());
        label = new JLabel();
        checkBox =  new JCheckBox();
        add(label, BorderLayout.CENTER);
        add(checkBox, BorderLayout.EAST);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TODOItem> list, TODOItem value, int index, boolean isSelected, boolean cellHasFocus)
    {
        label.setText(value.getText());
        checkBox.setSelected(value.getStatus());

        if (isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
