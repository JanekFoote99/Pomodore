package Pomodoro;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pomodoro extends JFrame
{
    private JPanel main;
    private JTextField timerPresetsTextField;
    private JButton Preset3Button;
    private JButton Preset2Button;
    private JButton Preset1Button;
    private JProgressBar TimerBar;
    private JTextField customTimerTextField;
    private JTextField workTimeTextField;
    private JTextField breakTimeTextField;
    private JButton Start;
    private JButton Pause;
    private JButton Reset;

    public JTextField getTimerStatusText()
    {
        return TimerStatusText;
    }

    public JTextField getCycleDisplay()
    {
        return cycleDisplay;
    }

    public JTextField getNumCycleDisplay()
    {
        return numCycleDisplay;
    }

    public JTextField getNumPomodoroCycleDisplay()
    {
        return numPomodoroCycleDisplay;
    }

    private JTextField TimerStatusText;
    private JFormattedTextField breakTimeInput;
    private JButton SetInputButton;
    private JFormattedTextField workTimeInput;
    private JTextField cycleDisplay;
    private JTextField numCycleDisplay;
    private JTextField longBreakTimeTextField;
    private JTextField numCyclesTextField;
    private JTextField numCyclesPomodoroTextField;
    private JFormattedTextField longBreakTimeInput;
    private JFormattedTextField numCyclesInput;
    private JFormattedTextField numCyclesPomodoroInput;
    private JButton setAsPresetButton;
    private JTextField numPomodoroCycleDisplay;
    private JTextField presetInfoText;

    private static Pomodoro pomodoro;
    private static PomodoroManager manager;
    private PomodoroConfig config;
    private PomodoroConfig toSaveConfig;
    private static XMLParser configParser;

    private boolean savePreset = false;

    public Pomodoro()
    {
        Start.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                config.update(Integer.parseInt(numCyclesInput.getText()),
                        Float.parseFloat(workTimeInput.getText()),
                        Float.parseFloat(breakTimeInput.getText()),
                        Integer.parseInt(numCyclesPomodoroInput.getText()),
                        Float.parseFloat(longBreakTimeInput.getText()));
                manager.startPomodoro(config);
            }
        });

        Reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                config.update(Integer.parseInt(numCyclesInput.getText()),
                        Float.parseFloat(workTimeInput.getText()),
                        Float.parseFloat(breakTimeInput.getText()),
                        Integer.parseInt(numCyclesPomodoroInput.getText()),
                        Float.parseFloat(longBreakTimeInput.getText()));
                manager.SetConfig(config);
                manager.resetPomodoro();
            }
        });

        Pause.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                manager.pausePomodoro();
            }
        });

        SetInputButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Set Work Time
                try
                {
                    int workTimeVal = Integer.parseInt(workTimeInput.getText());
                    if (workTimeVal < 0 || workTimeVal > 120)
                    {
                        JOptionPane.showMessageDialog(null, "Work Time unchanged, enter a value between 0 and 120");
                    } else
                    {
                        config.SetWorkTime(workTimeVal);
                    }
                } catch (NumberFormatException exception)
                {
                    JOptionPane.showMessageDialog(null, "Work Time not an Integer");
                }
                // Set Break Time
                try
                {
                    int breakTimeVal = Integer.parseInt(breakTimeInput.getText());
                    if (breakTimeVal < 0 || breakTimeVal > 30)
                    {
                        JOptionPane.showMessageDialog(null, "Pause Time unchanged, enter a value between 0 and 30");
                    } else
                    {
                        config.SetBreakTime(breakTimeVal);
                    }
                } catch (NumberFormatException exception)
                {
                    JOptionPane.showMessageDialog(null, "Pause Time not an Integer");
                }
                // Set Long Pause Time
                try
                {
                    int longBreakTimeVal = Integer.parseInt(longBreakTimeInput.getText());
                    if (longBreakTimeVal < 0 || longBreakTimeVal > 30)
                    {
                        JOptionPane.showMessageDialog(null, "Long Pause Time unchanged, enter a value between 0 and 30");
                    } else
                    {
                        config.SetLongBreakTime(longBreakTimeVal);
                    }
                } catch (NumberFormatException exception)
                {
                    JOptionPane.showMessageDialog(null, "Long Pause Time not an Integer");
                }
                // Set Number of Cycles
                try
                {
                    int numCycles = Integer.parseInt(numCyclesInput.getText());
                    if (numCycles < 0 || numCycles > 12)
                    {
                        JOptionPane.showMessageDialog(null, "Number of Cycles unchanged, enter a value between 0 and 12");
                    } else
                    {
                        config.SetNumberOfCycles(numCycles);
                    }
                } catch (NumberFormatException exception)
                {
                    JOptionPane.showMessageDialog(null, "Number of Cycles not an Integer");
                }
                // Set Number of Pomodoro Cycles
                try
                {
                    int numPomodoroCycles = Integer.parseInt(numCyclesPomodoroInput.getText());
                    if (numPomodoroCycles < 0 || numPomodoroCycles > 4)
                    {
                        JOptionPane.showMessageDialog(null, "Number of Pomodoro Cycles unchanged, enter a value between 0 and 4");
                    } else
                    {
                        config.SetNumberOfPomodoroCycles(numPomodoroCycles);
                    }
                } catch (NumberFormatException exception)
                {
                    JOptionPane.showMessageDialog(null, "Number of Pomodoro Cycles not an Integer");
                }
            }
        });
        Preset1Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (savePreset)
                {
                    savePreset(0);
                } else
                {
                    PomodoroConfig config = configParser.readPreset(0);

                    setTextFields(config);
                }
            }
        });
        Preset2Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (savePreset)
                {
                    savePreset(1);
                } else
                {
                    PomodoroConfig config = configParser.readPreset(1);

                    setTextFields(config);
                }
            }
        });
        Preset3Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (savePreset)
                {
                    savePreset(2);
                } else
                {
                    PomodoroConfig config = configParser.readPreset(2);

                    setTextFields(config);
                }
            }
        });

        /*TODO: After clicking the Set as Preset Button the User should be able to Click on any given Preset
                to change it. After clicking the given Preset a prompt should notify the user that the selected Preset
                will be deleted. The Presets should be saved to a XML File so that the saved Presets remain the same
                after starting the app again. */
        setAsPresetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (savePreset)
                {
                    setPresetButtonColors(Color.WHITE);

                    savePreset = false;
                    presetInfoText.setEnabled(false);
                } else
                {
                    float worktime = Float.parseFloat(workTimeInput.getText().trim());
                    float breaktime = Float.parseFloat(breakTimeInput.getText().trim());
                    float longbreaktime = Float.parseFloat(longBreakTimeInput.getText().trim());
                    int numcycles = Integer.parseInt(numCyclesInput.getText().trim());
                    int numcyclespomodoro = Integer.parseInt(numCyclesPomodoroInput.getText().trim());

                    toSaveConfig = new PomodoroConfig(numcycles, worktime, breaktime, numcyclespomodoro, longbreaktime);

                    setPresetButtonColors(Color.YELLOW);

                    savePreset = true;
                    presetInfoText.setEnabled(true);
                }
            }
        });

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                PomodoroConfig toSave = new PomodoroConfig();

                toSave.workTime = Integer.parseInt(workTimeInput.getText().trim());
                toSave.breakTime = Integer.parseInt(breakTimeInput.getText().trim());
                toSave.breakTimePomodoro = Integer.parseInt(longBreakTimeInput.getText().trim());
                toSave.numCycles = Integer.parseInt(numCyclesInput.getText().trim());
                toSave.numPomodoroCycles = Integer.parseInt(numCyclesPomodoroInput.getText().trim());

                configParser.writeTextFields(toSave);
            }
        });
    }

    private void savePreset(int presetId)
    {
        configParser.writePreset(toSaveConfig, presetId);
        setButtonTextField(presetId, toSaveConfig);

        savePreset = false;
        presetInfoText.setEnabled(false);
        setPresetButtonColors(Color.WHITE);
    }

    private void setPresetButtonColors(Color color)
    {
        Preset1Button.setBackground(color);
        Preset2Button.setBackground(color);
        Preset3Button.setBackground(color);
    }

    private void setTextFields(PomodoroConfig newConfig)
    {
        pomodoro.workTimeInput.setText(String.format("%s", (long) newConfig.workTime));
        pomodoro.breakTimeInput.setText(String.format("%s", (long) newConfig.breakTime));
        pomodoro.longBreakTimeInput.setText(String.format("%s", (long) newConfig.breakTimePomodoro));
        pomodoro.numCyclesInput.setText(Integer.toString(newConfig.numCycles));
        pomodoro.numCyclesPomodoroInput.setText(Integer.toString(newConfig.numPomodoroCycles));
    }

    private void setButtonTextField(int presetId, PomodoroConfig newConfig)
    {
        String formattedText = STR."\{String.format("%s", (long) newConfig.workTime)}/\{String.format("%s", (long) newConfig.breakTime)}/\{String.format("%s", (long) newConfig.breakTimePomodoro)}/\{Integer.toString(newConfig.numCycles)}/\{Integer.toString((newConfig.numPomodoroCycles))}";

        if(presetId == 0)
            Preset1Button.setText(formattedText);
        else if(presetId == 1)
            Preset2Button.setText(formattedText);
        else if(presetId == 2)
            Preset3Button.setText(formattedText);
    }

    private static void setup()
    {
        configParser = new XMLParser("./PomodoroPresets.xml", "./PomodoroSave.xml");
        pomodoro = new Pomodoro();
        pomodoro.createAndShowGUI();
        //pomodoro.setImages();
        manager = new PomodoroManager(pomodoro.config, pomodoro);
    }

    private void setImages()
    {
        try
        {
            BufferedImage startButtonIcon = ImageIO.read(new File("Assets/start.png"));
            pomodoro.Start.setIcon(new ImageIcon(startButtonIcon));
            pomodoro.Start.setBorder(BorderFactory.createEmptyBorder());
            pomodoro.Start.setContentAreaFilled(false);
            pomodoro.Start.setOpaque(true);
            pomodoro.Start.setPreferredSize(new Dimension(10, 10));
            pomodoro.Start.setText("");
        } catch (IOException e)
        {
            System.out.println("couldn't load image");
        }
    }

    private void createAndShowGUI()
    {
        pomodoro.setTitle("Pomodoro Timer");
        pomodoro.setSize(1600, 920);
        pomodoro.setVisible(true);
        pomodoro.setContentPane(pomodoro.main);
        pomodoro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pomodoro.config = new PomodoroConfig(4, 25.0f, 5.0f, 2, 20.0f);
        // Center window
        pomodoro.setLocationRelativeTo(null);

        setButtonTextFieldsOnStartup();
        setCustomTimerTextField();
    }

    private void setButtonTextFieldsOnStartup()
    {
        setButtonTextField(0, configParser.readPreset(0));
        setButtonTextField(1, configParser.readPreset(1));
        setButtonTextField(2, configParser.readPreset(2));
    }

    private void setCustomTimerTextField()
    {
        PomodoroConfig toWriteConfig = configParser.readTextFields();

        workTimeInput.setText(String.format("%s", (long) toWriteConfig.workTime));
        breakTimeInput.setText(String.format("%s", (long) toWriteConfig.breakTime));
        longBreakTimeInput.setText(String.format("%s", (long) toWriteConfig.breakTimePomodoro));
        numCyclesInput.setText(Integer.toString(toWriteConfig.numCycles));
        numCyclesPomodoroInput.setText(Integer.toString(toWriteConfig.numPomodoroCycles));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Pomodoro::setup);
    }
}
