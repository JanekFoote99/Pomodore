package main.Pomodore;

import main.Pomodore.TODOList.TODOItem;
import main.Pomodore.TODOList.TODOList;

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
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

class MonitorConfig
{
    public int x;
    public int y;
    public int width;
    public int height;

    public MonitorConfig(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
};

public class Pomodore extends JFrame
{
    private JPanel main;
    private JTextField timerPresetsTextField;
    private JButton Preset3Button;
    private JButton Preset2Button;
    private JButton Preset1Button;
    private JProgressBar timerBar;
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

    public JProgressBar getTimerBar()
    {
        return timerBar;
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
    private JTextField todoText;
    private JPanel TODOListPanel;

    private static Pomodore pomodore;
    private static PomodoroManager manager;
    private PomodoroConfig config;
    private PomodoroConfig toSaveConfig;
    private static XMLParser configParser;
    private MonitorConfig monitorConfig;
    private static TODOList todoList;

    private boolean savePreset = false;

    public Pomodore()
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
                // Save Timer config
                {
                    PomodoroConfig toSave = new PomodoroConfig();

                    toSave.workTime = Integer.parseInt(workTimeInput.getText().trim());
                    toSave.breakTime = Integer.parseInt(breakTimeInput.getText().trim());
                    toSave.breakTimePomodoro = Integer.parseInt(longBreakTimeInput.getText().trim());
                    toSave.numCycles = Integer.parseInt(numCyclesInput.getText().trim());
                    toSave.numPomodoroCycles = Integer.parseInt(numCyclesPomodoroInput.getText().trim());

                    configParser.writeTextFields(toSave);
                }

                // Save TODOList Elements
                {
                    ArrayList<TODOItem> items = todoList.getTodos();

                    configParser.writeTodos(items);
                }

                // Save current window Position, Width and monitorID
                {
                    Point windowPosition = pomodore.getLocation();

                    GraphicsConfiguration gc = pomodore.getGraphicsConfiguration();
                    GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

                    int x = windowPosition.x;
                    int y = windowPosition.y;
                    int width = pomodore.getWidth();
                    int height = pomodore.getHeight();

                    // Clamp so that everything is visible on startup
                    width = Math.min(2560, Math.max(width, 1100));
                    height = Math.min(1440, Math.max(height, 600));

                    configParser.writeMonitorInformation(new MonitorConfig(x, y, width, height));
                }
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
        pomodore.workTimeInput.setText(String.format("%s", (long) newConfig.workTime));
        pomodore.breakTimeInput.setText(String.format("%s", (long) newConfig.breakTime));
        pomodore.longBreakTimeInput.setText(String.format("%s", (long) newConfig.breakTimePomodoro));
        pomodore.numCyclesInput.setText(Integer.toString(newConfig.numCycles));
        pomodore.numCyclesPomodoroInput.setText(Integer.toString(newConfig.numPomodoroCycles));
    }

    private void setButtonTextField(int presetId, PomodoroConfig newConfig)
    {
        String formattedText = String.format("%s", (long) newConfig.workTime) + "/" +
                String.format("%s", (long) newConfig.breakTime) + "/" +
                String.format("%s", (long) newConfig.breakTimePomodoro) + "/" +
                String.format("%s", (long) newConfig.numCycles) + "/" +
                String.format("%s", (long) newConfig.numPomodoroCycles);

        if (presetId == 0)
            Preset1Button.setText(formattedText);
        else if (presetId == 1)
            Preset2Button.setText(formattedText);
        else if (presetId == 2)
            Preset3Button.setText(formattedText);
    }

    private static void setup()
    {
        configParser = new XMLParser("./PomodoreConfig.xml");
        pomodore = new Pomodore();

        ArrayList<TODOItem> todoItemList = configParser.readTodos();

        todoList = new TODOList(pomodore.TODOListPanel, todoItemList);

        pomodore.createAndShowGUI();
        //pomodoro.setImages();
        manager = new PomodoroManager(pomodore.config, pomodore);
    }

    private void setImages()
    {
        try
        {
            BufferedImage startButtonIcon = ImageIO.read(new File("Assets/start.png"));
            pomodore.Start.setIcon(new ImageIcon(startButtonIcon));
            pomodore.Start.setBorder(BorderFactory.createEmptyBorder());
            pomodore.Start.setContentAreaFilled(false);
            pomodore.Start.setOpaque(true);
            pomodore.Start.setPreferredSize(new Dimension(10, 10));
            pomodore.Start.setText("");
        } catch (IOException e)
        {
            System.out.println("couldn't load image");
        }
    }

    private void createAndShowGUI()
    {
        pomodore.setTitle("Pomodoro Timer");
        pomodore.restoreWindowPosition();
        pomodore.setVisible(true);
        pomodore.setContentPane(pomodore.main);
        pomodore.getContentPane().setBackground(Color.GRAY);
        pomodore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pomodore.config = new PomodoroConfig(4, 25.0f, 5.0f, 2, 20.0f);

        timerBar.setForeground(Color.BLACK);

        setButtonTextFieldsOnStartup();
        setCustomTimerTextField();
    }

    private void restoreWindowPosition()
    {
        monitorConfig = configParser.readMonitorInformation();

        pomodore.setSize(monitorConfig.width, monitorConfig.height);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] monitors = ge.getScreenDevices();

        pomodore.setLocation(monitorConfig.x, monitorConfig.y);
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
        SwingUtilities.invokeLater(Pomodore::setup);
    }
}
