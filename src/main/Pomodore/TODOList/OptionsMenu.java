package main.Pomodore.TODOList;

import javax.swing.*;
import java.awt.*;

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

        JPanel soundPanel = createSoundPanel();
        JPanel saveConfigOnCloseup = createConfigSavePanel();

        optionsMenu.add(soundPanel);
        optionsMenu.add(saveConfigOnCloseup);

        this.add(optionsMenu);
    }

    // Creates the Options Entry for Volume control
    private JPanel createSoundPanel()
    {
        JPanel soundPanel = new JPanel(new BorderLayout());

        JMenuItem soundVolume = new JMenuItem("Sound Volume");
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setPreferredSize(new Dimension(100, 50));

        soundPanel.add(soundVolume, BorderLayout.WEST);
        soundPanel.add(volumeSlider, BorderLayout.EAST);

        return soundPanel;
    }

    // Creates the Options Entry to enable/disable saving the Todo/Timer Variables
    private JPanel createConfigSavePanel()
    {
        JPanel saveConfigPanel = new JPanel(new BorderLayout());

        JMenuItem saveConfigOnCloseup = new JMenuItem("Save Config on Closeup");
        JCheckBox saveConfigCheckbox = new JCheckBox();

        saveConfigPanel.add(saveConfigOnCloseup, BorderLayout.WEST);
        saveConfigPanel.add(saveConfigCheckbox, BorderLayout.EAST);

        return saveConfigPanel;
    }
}
