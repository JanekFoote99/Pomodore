package main.Pomodore.Options;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class OptionsMenu extends JFrame
{
    public List<OptionsEntry> optionsEntryList;

    public OptionsMenu()
    {
        optionsEntryList = new ArrayList<>();
        optionsEntryList.add(new OptionsEntry("Sound Volume", JSlider.class));
        optionsEntryList.add(new OptionsEntry("Save Timers on Close", JCheckBox.class));
    }

    public OptionsEntry getSoundVolumeEntry()
    {
        return optionsEntryList.getFirst();
    }

    public OptionsEntry getSaveConfigEntry()
    {
        return optionsEntryList.get(1);
    }

    public boolean getSaveTimersEntry()
    {
        int saveConfigState = optionsEntryList.get(1).getValue();
        return saveConfigState == 1;
    }
}
