package Pomodore;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PomodoroCycle
{
    private boolean inWork;
    private Timer timer;

    /* Example for a Pomodoro with 4 Iterations of Work/Break Cycles
        Work -> Break ->
        Work -> Break ->
        Work -> Break ->
        Work -> Break/Long_Break
     */
    public enum CycleType
    {
        WORK,
        BREAK,
        LONG_BREAK,
        OVER
    }

    // Flag to determine which Cycle the variable startTime represents
    private CycleType curCycle;

    public CycleType getCurCycle () { return curCycle; }

    private long startTime = -1;
    private long timerTime = -1;

    private float workTime;
    private float breakTime;
    // Break Time between two Pomodoro Cycles
    private float breakTimePomodoro;

    public float getWorkTime()
    {
        return workTime;
    }

    public void setWorkTime(float workTime)
    {
        this.workTime = workTime;
    }

    public float getBreakTime()
    {
        return breakTime;
    }

    public void setBreakTime(float breakTime)
    {
        this.breakTime = breakTime;
    }

    public float getBreakTimePomodoro()
    {
        return breakTimePomodoro;
    }

    public void setBreakTimePomodoro(float breakTimePomodoro)
    {
        this.breakTimePomodoro = breakTimePomodoro;
    }

    public int getNumCyclesPomodoro()
    {
        return numCyclesPomodoro;
    }

    public void setNumCyclesPomodoro(int numCyclesPomodoro)
    {
        this.numCyclesPomodoro = numCyclesPomodoro;
    }

    public int getNumCycles()
    {
        return numCycles;
    }

    public void setNumCycles(int numCycles)
    {
        this.numCycles = numCycles;
    }


    // Number of work/time cycles it takes to complete one Pomodoro
    private int numCyclesPomodoro;
    private int curNumCyclesPomodoro = 1;

    private int numCycles;
    private int curNumCycles = 1;

    private long remainingTime = -1;

    public long getRemainingTime()
    {
        return remainingTime;
    }

    public int getCurNumCycles()
    {
        return curNumCycles;
    }

    private JTextField timeDisplay;
    private JTextField numCyclesDisplay;
    private JTextField numPomodoroCycleDisplay;
    private JTextField curCycleDisplay;
    private JFrame frame;
    private JProgressBar timerBarDisplay;

    public boolean timerPaused;
    public boolean timerReset;
    private long timerPauseTime = 0;

    static final String B_GREEN = "#238823";
    static final String B_RED = "#D2222D";
    static final String B_YELLOW = "#FFBF00";

    public PomodoroCycle()
    {
        workTime = MinutesToMilliseconds(25);
        breakTime = MinutesToMilliseconds(5);
        numCyclesPomodoro = 4;
        numCycles = 1;
    }

    // Testing
    public PomodoroCycle(PomodoroConfig config)
    {
        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.numCycles = config.numCycles;
        this.curCycle = CycleType.WORK;

        setupTimer();
    }

    public PomodoroCycle(Pomodore frame, float workTime, float breakTime, float longBreakTime, int numCycles, int numCyclesPomodoro)
    {
        this.frame = frame;
        this.numPomodoroCycleDisplay = frame.getNumPomodoroCycleDisplay();
        this.timeDisplay = frame.getTimerStatusText();
        this.curCycleDisplay = frame.getCycleDisplay();
        this.numCyclesDisplay = frame.getNumCycleDisplay();
        this.timerBarDisplay = frame.getTimerBar();

        this.workTime = workTime;
        this.breakTime = breakTime;
        this.breakTimePomodoro = longBreakTime;
        this.numCycles = numCycles;
        this.numCyclesPomodoro = numCyclesPomodoro;

        this.curCycle = CycleType.WORK;

        setupTimer();
    }

    public PomodoroCycle(Pomodore frame, PomodoroConfig config)
    {
        this.frame = frame;
        this.numPomodoroCycleDisplay = frame.getNumPomodoroCycleDisplay();
        this.timeDisplay = frame.getTimerStatusText();
        this.curCycleDisplay = frame.getCycleDisplay();
        this.numCyclesDisplay = frame.getNumCycleDisplay();
        this.timerBarDisplay = frame.getTimerBar();

        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.numCycles = config.numCycles;
        this.curCycle = CycleType.WORK;

        setupTimer();
    }

    private void setup()
    {

    }

    private void setupDisplays()
    {

    }

    private void setupTimer()
    {
        timer = new Timer(10, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (startTime < 0)
                {
                    startTime = System.currentTimeMillis();
                }

                long curTime = System.currentTimeMillis();
                long delta = curTime - startTime;

                if (delta >= MinutesToMilliseconds(timerTime))
                {
                    // Clamp so that teh time is correctly displayed after finishing
                    delta = MinutesToMilliseconds(timerTime);

                    // Determine and configure the next timer according to the transition of the Cycles
                    curCycle = getCycle(curCycle);
                    // new Timer begins, reset startTime
                    startTime = curTime;

                    if (curCycle == CycleType.OVER)
                    {
                        timer.stop();
                    } else
                    {
                        timer.restart();
                    }
                }

                remainingTime = MinutesToMilliseconds(timerTime) - delta;

                long temp = MinutesToMilliseconds(timerTime);

                float percentage = (1 - (float) remainingTime / temp) * 100;
                timerBarDisplay.setValue((int) percentage);

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                timeDisplay.setText(df.format(remainingTime) + " / " + df.format(MinutesToMilliseconds(timerTime)));
                curCycleDisplay.setText(curCycle.toString());
                numPomodoroCycleDisplay.setText(curNumCyclesPomodoro + " / " + numCyclesPomodoro);
                numCyclesDisplay.setText(curNumCycles + " / " + numCycles);
            }
        });
        timer.setInitialDelay(0);
    }

    void playCycleTransitionSound(String soundpath)
    {
        try
        {
            File soundFile = new File(soundpath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            throw new RuntimeException(e);
        }
    }

    private CycleType getCycle(CycleType curCycle)
    {
        CycleType newCycle = curCycle;

        switch (curCycle)
        {
            case WORK:
                if (curNumCycles != numCycles)
                {
                    newCycle = CycleType.BREAK;
                    // set time of the next Cycle to breakTime
                    timerTime = (long) breakTime;

                    playCycleTransitionSound("./Assets/audio/endBell.wav");
                    frame.getContentPane().setBackground(Color.decode(B_RED));
                } else
                {
                    newCycle = CycleType.LONG_BREAK;
                    // set time of the next Cycle to breakTime
                    timerTime = (long) breakTimePomodoro;

                    playCycleTransitionSound("Assets/audio/success.wav");
                    frame.getContentPane().setBackground(Color.decode(B_YELLOW));
                }
                break;
            case BREAK:
                newCycle = CycleType.WORK;
                timerTime = (long) workTime;
                curNumCycles++;

                frame.getContentPane().setBackground(Color.decode(B_GREEN));
                playCycleTransitionSound("./Assets/audio/startBell.wav");
                break;
            case LONG_BREAK:
                if (curNumCyclesPomodoro != numCyclesPomodoro)
                {
                    newCycle = CycleType.WORK;
                    timerTime = (long) workTime;

                    curNumCycles = 1;
                    curNumCyclesPomodoro++;

                    playCycleTransitionSound("./Assets/audio/startBell.wav");
                    frame.getContentPane().setBackground(Color.decode(B_GREEN));
                } else
                {
                    newCycle = CycleType.OVER;

                    // Add Bell to signal finish
                    resetTimer();
                    frame.getContentPane().setBackground(Color.GRAY);
                }
                break;
            case OVER:
                newCycle = CycleType.WORK;
                timerTime = (long) workTime;

                playCycleTransitionSound("./Assets/audio/startBell.wav");
                frame.getContentPane().setBackground(Color.decode(B_GREEN));
        }

        return newCycle;
    }

    public void pauseTimer()
    {
        if (!timerReset)
        {
            // Start tracking time after hitting the pause button
            timerPauseTime = System.currentTimeMillis();
            timerPaused = true;

            timer.stop();
        }
    }

    public void continueTimer()
    {
        if (!timerReset)
        {
            /* Special case
             the Time between pausing the timer and resetting would be getting added to the remaining time
             so the timer keeps running "in the back" while not displaying the change */

            // Calculate dif between pause and resume
            timerPauseTime = System.currentTimeMillis() - timerPauseTime;
            // Add the needed time to the startTime, so that the remaining time is calculated correctly
            startTime += timerPauseTime;

            timerPaused = false;

            timer.start();
        }
    }

    public void resetTimer()
    {
        startTime = -1;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        timeDisplay.setText(df.format(MinutesToMilliseconds(workTime)));
        timer.stop();

        curNumCycles = 1;
        curNumCyclesPomodoro = 1;

        frame.getContentPane().setBackground(Color.GRAY);
        timerPaused = true;
        timerReset = true;
    }

    // TODO: Clean up and fix Code
    // Starting the Cycle the first time causes to go through the first if branch due to parameters
    // Setting timerReset to false at the start causes to bug the pause function due to resetting the timer
    public void startWorkCycle()
    {
        // Reset has priority so that when both flags timerReset and timerPaused
        // are true, the timer rather restarts than continue
        if (timerReset)
        {
            timerReset = false;
            timerPaused = false;

            numCyclesDisplay.setText(curNumCycles + " / " + numCycles);
            numPomodoroCycleDisplay.setText(curNumCyclesPomodoro + " / " + numCyclesPomodoro);
            curCycleDisplay.setText(curCycle.toString());

            frame.getContentPane().setBackground(Color.decode(B_GREEN));
            timerTime = (long) workTime;
            timer.restart();
        } else if (timerPaused)
        {
            continueTimer();
        } else
        {
            // TODO: create abstraction
            numCyclesDisplay.setText(curNumCycles + " / " + numCycles);
            numPomodoroCycleDisplay.setText(curNumCyclesPomodoro + " / " + numCyclesPomodoro);
            curCycleDisplay.setText(curCycle.toString());
            //---------------------------------------------------------
            timerTime = (long) workTime;
            timer.start();
            frame.getContentPane().setBackground(Color.decode(B_GREEN));
        }
    }

    public void SetConfig(PomodoroConfig config)
    {
        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.breakTimePomodoro = config.breakTimePomodoro;
        this.numCycles = config.numCycles;
        this.numCyclesPomodoro = config.numPomodoroCycles;
    }

    // Helper function to convert minutes to milliseconds
    private <T extends Number> long MinutesToMilliseconds(T minutes)
    {
        return minutes.longValue() * 60 * 1000;
    }
}
