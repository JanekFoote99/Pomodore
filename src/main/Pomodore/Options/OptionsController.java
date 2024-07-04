package main.Pomodore.Options;

import javax.swing.*;

public class OptionsController
{
    private final OptionsView view;
    private OptionsMenu menu;

    public OptionsController()
    {
        this.menu = new OptionsMenu();
        this.view = new OptionsView(this.menu);
    }

    public void showView(JFrame parent)
    {
        this.view.setLocationRelativeTo(parent);

        this.view.showView(parent);
    }

    public void setOptionsMenu(OptionsMenu menu)
    {
        this.menu = menu;
    }

    public int getSoundVolume()
    {
        return menu.optionsEntryList.getFirst().getValue();
    }

    public OptionsView getView()
    {
        return view;
    }

    public OptionsMenu getMenu()
    {
        return menu;
    }
}
