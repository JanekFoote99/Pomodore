package main.Pomodore.Options;

import javax.swing.*;
import java.awt.*;

public class OptionsEntry extends JPanel
{
    private final int TEXTFIELD_WIDTH = 200;
    private final int COMPONENT_WIDTH = 100;
    private final int ITEM_HEIGHT = 30;

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
                JCheckBox checkBox = new JCheckBox();
                checkBox.setHorizontalAlignment(JCheckBox.CENTER);
                optionsComponent = checkBox;
            } else
            {
                System.out.println("Unsupported component type: " + componentType.getName());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        // Change size of Items
        optionsName.setPreferredSize(new Dimension(TEXTFIELD_WIDTH, ITEM_HEIGHT));
        optionsComponent.setPreferredSize(new Dimension(COMPONENT_WIDTH, ITEM_HEIGHT));

        componentPanel.add(optionsName, BorderLayout.WEST);
        componentPanel.add(optionsComponent, BorderLayout.EAST);

        return componentPanel;
    }

    public String getOptionsName()
    {
        return optionsName.getName();
    }
}