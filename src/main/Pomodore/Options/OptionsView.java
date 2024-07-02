package main.Pomodore.Options;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OptionsView extends JFrame
{
    List<OptionsEntry> optionEntries;

    public OptionsView(OptionsMenu menu)
    {
        setTitle("Options");
        optionEntries = new ArrayList<>(menu.optionsEntryList);
        initUI();
    }

    private void initUI()
    {
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;

        int row = 0;
        for (OptionsEntry entry : optionEntries)
        {
            JPanel panel = entry.createPanel();
            panel.setPreferredSize(new Dimension(350, 30));
            gbc.gridy = row++;
            optionsPanel.add(panel, gbc);
        }

        gbc.weighty = 1.0;
        optionsPanel.add(Box.createVerticalGlue(), gbc);

        add(optionsPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(500, 200));
        pack();
    }

    public void showView(JFrame parent)
    {
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @Override
    public void dispose()
    {
        removeAll();
        super.dispose();
    }
}
