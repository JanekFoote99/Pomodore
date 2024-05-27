package main.Pomodore.TODOList;

import javax.swing.*;

public class TODOItem
{
    private JTextField text;
    // Checkmark weather item is finished or not
    private JCheckBox checkbox;

    TODOItem(String text)
    {
        this.text = new JTextField(text);
        this.checkbox = new JCheckBox();
    }

    // Getters
    public JCheckBox getCheckBox()
    {
        return this.checkbox;
    }

    public JTextField getTextField()
    {
        return this.text;
    }

    // Setters
    public void setCheckbox(boolean checkbox)
    {
        this.checkbox.setSelected(checkbox);
    }

    public void setText(String text)
    {
        this.text.setText(text);
    }
}
