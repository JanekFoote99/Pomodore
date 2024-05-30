package main.Pomodore.TODOList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class TODOItem
{
    private JTextField textField;
    // Checkmark weather item is finished or not
    private final JCheckBox checkbox;
    private final JButton deleteButton;


    public TODOItem(String text)
    {

        this.textField = new JTextField(text);
        this.checkbox = new JCheckBox();
        this.checkbox.setHorizontalAlignment(JCheckBox.CENTER);
        this.deleteButton = new JButton("X");
        this.deleteButton.setBackground(Color.decode("#a52a2a"));
        this.deleteButton.setForeground(Color.WHITE);

        Font textFont = new Font(this.textField.getFont().getName(), this.textField.getFont().getStyle(), 20);
        this.textField.setFont(textFont);
    }

    // Getters
    public JCheckBox getCheckBox()
    {
        return this.checkbox;
    }

    public JTextField getTextField()
    {
        return this.textField;
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

    public void setTextField(String textField)
    {
        this.textField.setText(textField);
    }
}
