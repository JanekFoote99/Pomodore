package main.Pomodore.Options;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class OptionsMenu extends JFrame
{
    public OptionsMenu()
    {
        createMenu();
        this.setSize(400, 300);
        this.setVisible(true);
    }

    private void createMenu()
    {
        List<OptionsEntry> optionsEntryList = new ArrayList<>();

        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        optionsEntryList.add(new OptionsEntry("Sound Volume", JSlider.class));
        optionsEntryList.add(new OptionsEntry("Save Config on Closeup", JCheckBox.class));

        int row = 0;
        for (OptionsEntry entry : optionsEntryList) {
            JPanel panel = entry.createPanel();
            panel.setPreferredSize(new Dimension(350, 30));
            gbc.gridy = row++;
            optionsPanel.add(panel, gbc);
        }

        gbc.weighty = 1.0;
        optionsPanel.add(Box.createVerticalGlue(), gbc);

        add(optionsPanel, BorderLayout.CENTER);
    }
}
