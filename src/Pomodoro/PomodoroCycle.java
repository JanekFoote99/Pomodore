package Pomodoro;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
    enum CycleType
    {
        WORK,
        BREAK,
        LONG_BREAK,
        OVER
    }

    // Flag to determine which Cycle the variable startTime represents
    private CycleType curCycle;
    private long startTime = -1;
    private long timerTime = -1;

    private float workTime;
    private float breakTime;
    // Break Time between two Pomodoro Cycles
    private float breakTimePomodoro;
    // Number of work/time cycles it takes to complete one Pomodoro
    private int numCyclesPomodoro;
    private int curNumCyclesPomodoro = 1;

    private int numCycles;
    private int curNumCycles = 1;
    public int getCurNumCycles() { return curNumCycles; }

    private JTextField timeDisplay;
    private JTextField numCyclesDisplay;
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
    public PomodoroCycle(PomodoroConfig config){
        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.numCycles = config.numCycles;
        this.curCycle = CycleType.WORK;

        setupTimer();
    }

    public PomodoroCycle(Pomodoro frame, float workTime, float breakTime, int numCycles)
    {
        this.frame = frame;
        this.timeDisplay = frame.getTimerStatusText();
        this.curCycleDisplay = frame.getCycleDisplay();
        this.numCyclesDisplay = frame.getNumCycleDisplay();

        this.workTime = workTime;
        this.breakTime = breakTime;
        this.numCycles = numCycles;
        this.curCycle = CycleType.WORK;

        setupTimer();
    }

    public PomodoroCycle(Pomodoro frame, PomodoroConfig config)
    {
        this.frame = frame;
        this.timeDisplay = frame.getTimerStatusText();
        this.curCycleDisplay = frame.getCycleDisplay();
        this.numCyclesDisplay = frame.getNumCycleDisplay();

        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.numCycles = config.numCycles;
        this.curCycle = CycleType.WORK;

        setupTimer();
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

                    if(curCycle != CycleType.OVER)
                    {
                        timer.restart();
                    }
                    else
                    {
                        timer.stop();
                    }
                }

                long remainingTime = MinutesToMilliseconds(timerTime) - delta;

                //timerBarDisplay.setValue(50);

                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                timeDisplay.setText(df.format(remainingTime) + " / " + df.format(MinutesToMilliseconds(timerTime)));
                curCycleDisplay.setText(curCycle.toString());
                numCyclesDisplay.setText(curNumCycles + " / " + numCycles);
            }
        });
        timer.setInitialDelay(0);
    }

    void playCycleTransitionSound(String soundpath)
    {
        try{
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

    private CycleType getCycle(CycleType curCycle){
        CycleType newCycle = curCycle;

        switch (curCycle)
        {
            case WORK:
                if (curNumCycles != numCycles)
                {
                    newCycle = CycleType.BREAK;
                    // set time of the next Cycle to breakTime
                    timerTime = (long)breakTime;

                    frame.getContentPane().setBackground(Color.decode(B_RED));
                } else
                {
                    newCycle = CycleType.LONG_BREAK;
                    // set time of the next Cycle to breakTime
                    timerTime = (long)breakTimePomodoro;

                    frame.getContentPane().setBackground(Color.decode(B_YELLOW));
                }
                playCycleTransitionSound("./Assets/audio/endBell.wav");
                break;
            case BREAK:
                newCycle = CycleType.WORK;
                timerTime = (long)workTime;
                curNumCycles++;

                frame.getContentPane().setBackground(Color.decode(B_GREEN));
                playCycleTransitionSound("./Assets/audio/startBell.wav");
                break;
            case LONG_BREAK:
                if(curNumCyclesPomodoro != numCycles){
                    newCycle = CycleType.WORK;
                    timerTime = (long)workTime;
                    curNumCyclesPomodoro++;

                    playCycleTransitionSound("./Assets/audio/startBell.wav");
                    frame.getContentPane().setBackground(Color.decode(B_GREEN));
                } else {
                    newCycle = CycleType.OVER;

                    // Add Bell to signal finish
                    //playCycleTransitionSound("Assets/audio/endBell.wav");
                    frame.getContentPane().setBackground(Color.WHITE);
                }
                break;
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
            // Calculate dif between pause and resume
            timerPauseTime = System.currentTimeMillis() - timerPauseTime;
            // Add the needed time to the startTime, so that the remaining time is calculated correctly
            startTime += timerPauseTime;

            timerPaused = false;

            timer.start();
        }
        // Special case
        // the Time between pausing the timer and resetting would be getting added to the remaining time
    }

    public void resetTimer()
    {
        startTime = -1;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        timeDisplay.setText(df.format(MinutesToMilliseconds(workTime)));
        timer.stop();

        curNumCycles = 1;
        timerPaused = true;
        timerReset = true;
    }

    // TODO: Clean up Code
    public void startWorkCycle()
    {
        if (timerReset)
        {
            timerReset = false;
            timerPaused = false;
            timerTime = (long)workTime;
            timer.restart();
        } else if (timerPaused)
        {
            continueTimer();
        } else
        {
            // TODO: create abstraction
            numCyclesDisplay.setText(curNumCycles + " / " + numCycles);
            curCycleDisplay.setText(curCycle.toString());
            //---------------------------------------------------------
            timerTime = (long)workTime;
            timer.start();
            frame.getContentPane().setBackground(Color.decode(B_GREEN));
        }
    }

    public void startBreakCycle()
    {

    }

    public void startLongBreakCycle()
    {

    }

    void SetConfig(PomodoroConfig config){
        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.breakTimePomodoro = config.breakTimePomodoro;
        this.numCycles = config.numCycles;
        this.numCyclesPomodoro = config.numPomodoroCycles;
    }

    void SetWorkTime(float workTime)
    {
        // TODO: Set Bounds for Worktime
        this.workTime = workTime;
    }

    void SetBreakTime(float breakTime)
    {
        // TODO: Set Bounds for Breaktime
        this.breakTime = breakTime;
    }

    void SetBreakTimePomodoro(float breakTimePomodoro)
    {
        this.breakTimePomodoro = breakTimePomodoro;
    }

    void SetNumCyclesPomodoro(int numCyclesPomodoro)
    {
        this.numCyclesPomodoro = numCyclesPomodoro;
    }

    void SetNumCycles(int numCycles)
    {
        this.numCycles = numCycles;
    }

    // Helper function to convert minutes to milliseconds
    private <T extends Number> long MinutesToMilliseconds(T minutes)
    {
        return minutes.longValue() * 60 * 1000;
    }
}
