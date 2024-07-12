package main.Pomodore.Options;

import javax.swing.*;
import java.awt.*;

public class OptionsEntry extends JPanel
{
    private final JMenuItem optionsName;
    private JComponent optionsComponent;
    private final Class<? extends JComponent> componentType;

    public OptionsEntry(String optionsName, Class<? extends JComponent> componentType)
    {
        this.optionsName = new JMenuItem(optionsName);
        this.componentType = componentType;
    }

    public JPanel createPanel()
    {
        JPanel componentPanel = new JPanel(new BorderLayout());
        componentPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        try
        {
            if (componentType == JSlider.class)
            {
                optionsComponent = new JSlider(0, 100, 50);
            } else if (componentType == JCheckBox.class)
            {
                // Create Dummy Checkbox to add a ChangeListener
                JCheckBox checkbox = new JCheckBox();
                checkbox.setHorizontalAlignment(JCheckBox.CENTER);

                optionsComponent = checkbox;
            } else
            {
                //System.out.println(STR."Unsupported component type: \{componentType.getName()}");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // Change size of Items
        int TEXTFIELD_WIDTH = 200;
        int ITEM_HEIGHT = 30;
        optionsName.setPreferredSize(new Dimension(TEXTFIELD_WIDTH, ITEM_HEIGHT));
        int COMPONENT_WIDTH = 100;
        optionsComponent.setPreferredSize(new Dimension(COMPONENT_WIDTH, ITEM_HEIGHT));

        componentPanel.add(optionsName, BorderLayout.WEST);
        componentPanel.add(optionsComponent, BorderLayout.EAST);

        return componentPanel;
    }

    public int getValue()
    {
        if (componentType == JSlider.class)
        {
            // cast to be able to call JSlider methods
            JSlider comp = (JSlider) optionsComponent;
            return comp.getValue();
        } else if (componentType == JCheckBox.class)
        {
            JCheckBox box = (JCheckBox) optionsComponent;
            return box.isSelected() ? 1 : 0;
        } else
        {
            throw new RuntimeException("Unsupported type");
        }
    }

    public void setValue(int value)
    {
        if (componentType == JSlider.class)
        {
            ((JSlider) optionsComponent).setValue(value);
        } else if (componentType == JCheckBox.class)
        {
            JCheckBox checkbox = (JCheckBox) optionsComponent;
            checkbox.setSelected(value == 1);
        }
    }
}