package Pomodoro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PomodoroCycle
{

    private boolean inWork;
    private Timer timer;

    enum CycleType
    {
        WORK,
        BREAK,
        LONG_BREAK
    }

    // Flag to determine which Cycle the variable startTime represents
    private CycleType curType;
    private long startTime = -1;

    private float workTime;
    private float breakTime;

    // Break Time between two Pomodoros
    private float breakTimePomodoro;
    // Number of work/time cycles it takes to complete one Pomodoro
    private int numCyclesPomodoro;

    private int numCycles;

    private JTextField timeDisplay;

    public boolean timerPaused;
    private long timerPauseTime = 0;

    public PomodoroCycle()
    {
        workTime = MinutesToMilliseconds(25);
        breakTime = MinutesToMilliseconds(5);
        numCyclesPomodoro = 4;
        numCycles = 1;
    }

    public PomodoroCycle(JTextField timeDisplay, float workTime, float breakTime, int numCycles)
    {
        this.timeDisplay = timeDisplay;
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.numCycles = numCycles;

        if (timer == null)
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

                    if (delta >= MinutesToMilliseconds(workTime))
                    {
                        delta = MinutesToMilliseconds(workTime);
                        timer.stop();
                    }

                    long remainingTime = MinutesToMilliseconds(workTime) - delta;

                    SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                    timeDisplay.setText(df.format(remainingTime));
                }
            });
        }
    }

    public void pauseTimer()
    {
        // Start tracking time after hitting the pause button
        timerPauseTime = System.currentTimeMillis();
        timerPaused = true;

        timer.stop();
    }

    public void continueTimer(){
        // Calculate dif between pause and resume
        timerPauseTime = System.currentTimeMillis() - timerPauseTime;
        // Add the needed time to the startTime, so that the remaining time is calculated correctly
        startTime += timerPauseTime;

        timerPaused = false;

        timer.start();
    }

    public void resetTimer(){
        startTime = -1;
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        timeDisplay.setText(df.format(MinutesToMilliseconds(workTime)));
        timer.stop();
        timerPaused = true;
    }

    // TODO: start the Work Cycle and returns whether or not the cycle is finished
    public void startWorkCycle()
    {
        if(timerPaused){
            timer.restart();
        }else{
            timer.setInitialDelay(0);
            timer.start();
        }
    }

    public void startBreakCycle()
    {

    }

    public void startLongBreakCycle()
    {

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

    // Helper function to convert minutes to milliseconds
    private <T extends Number> long MinutesToMilliseconds(T minutes)
    {
        return minutes.longValue() * 60 * 1000;
    }
}
