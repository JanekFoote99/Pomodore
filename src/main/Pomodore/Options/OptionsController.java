package main.Pomodore.Options;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsController
{
    private static OptionsController INSTANCE;
    private static OptionsView view;
    private static OptionsMenu menu;

    public OptionsController()
    {
        menu = new OptionsMenu();
        view = new OptionsView(menu);
    }

    public void showView(JFrame parent)
    {
        view.setLocationRelativeTo(parent);

        view.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowStateChanged(WindowEvent e)
            {
                menu.updateMenu();
            }
        });

        view.showView(parent);
    }

    /*public int getSoundVolume()
    {

    }*/
}
