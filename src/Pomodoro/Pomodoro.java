package Pomodoro;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pomodoro extends JFrame
{

    private JPanel main;
    private JTextField timerPresetsTextField;
    private JButton a55_5_Button;
    private JButton a45_15_Button;
    private JButton a25_5_Button;
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

    private static Pomodoro pomodoro;
    private static PomodoroManager manager;
    private PomodoroConfig config;

    public Pomodoro()
    {
        Start.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                manager.startPomodoro(config);
            }
        });

        Reset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO
                manager.resetPomodoro();
            }
        });

        Pause.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO
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
                    int numPomodoroCycles = Integer.parseInt(numCyclesInput.getText());
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
        a25_5_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pomodoro.config.workTime = 25.0f;
                pomodoro.config.breakTime = 5.0f;
            }
        });
        a45_15_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pomodoro.config.workTime = 45.0f;
                pomodoro.config.breakTime = 15.0f;
            }
        });
        a55_5_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pomodoro.config.workTime = 55.0f;
                pomodoro.config.breakTime = 5.0f;
            }
        });
        //TODO
        setAsPresetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
    }

    private static void setup()
    {
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
        pomodoro.config = new PomodoroConfig(4, 25.0f, 5.0f, 1, 20.0f);
        // Center window
        pomodoro.setLocationRelativeTo(null);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Pomodoro::setup);
    }
}
