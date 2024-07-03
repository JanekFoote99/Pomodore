package main.Pomodore.Options;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class OptionsMenu extends JFrame
{
    public List<OptionsEntry> optionsEntryList;

    public OptionsMenu()
    {
        optionsEntryList = new ArrayList<>();
        optionsEntryList.add(new OptionsEntry("Sound Volume", JSlider.class));
        optionsEntryList.add(new OptionsEntry("Save Config on Close", JCheckBox.class));
    }

    public void setSoundVolume(int val)
    {
        optionsEntryList.getFirst().setValue(val);
    }

    public void setSaveConfigEntry(boolean flag)
    {
        if (flag)
        {
            optionsEntryList.get(1).setValue(1);
        } else
        {
            optionsEntryList.get(1).setValue(0);
        }
    }

    public OptionsEntry getSoundVolumeEntry()
    {
        return optionsEntryList.getFirst();
    }

    public OptionsEntry getSaveConfigEntry()
    {
        return optionsEntryList.get(1);
    }
}
