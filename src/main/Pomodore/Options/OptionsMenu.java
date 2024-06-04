package main.Pomodore.Options;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class OptionsMenu extends JMenuBar
{
    public OptionsMenu(JButton button)
    {
        createMenu();

        button.add(this);
    }

    private void createMenu()
    {
        JMenu optionsMenu = new JMenu("Options");

        this.add(optionsMenu);

        List<OptionsEntry> optionsEntryList = new ArrayList<>();

        optionsEntryList.add(new OptionsEntry("Sound Volume", JSlider.class));
        optionsEntryList.add(new OptionsEntry("Save Config on Closeup", JCheckBox.class));

        for (OptionsEntry entry : optionsEntryList)
        {
            optionsMenu.add(entry.createPanel());
        }

        this.add(optionsMenu);
    }
}
