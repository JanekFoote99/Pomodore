package main.Pomodore.TODOList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TODOItem
{
    private JTextField text;
    // Checkmark weather item is finished or not
    private JCheckBox checkbox;
    private JButton deleteButton;

    TODOItem(String text)
    {
        this.text = new JTextField(text);
        this.checkbox = new JCheckBox();
        this.checkbox.setHorizontalAlignment(JCheckBox.CENTER);
        this.deleteButton = new JButton("X");
        this.deleteButton.setBackground(Color.decode("#a52a2a"));
        this.deleteButton.setForeground(Color.WHITE);

        Font textFont = new Font(this.text.getFont().getName(), this.text.getFont().getStyle(), 20);
        this.text.setFont(textFont);
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

    public JButton getDeleteButton()
    {
        return this.deleteButton;
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
