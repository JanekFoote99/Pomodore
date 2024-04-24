package Pomodoro;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PomodoroCycle
{

    private boolean inWork;
    private Timer timer;

    enum CycleType {
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
        this.workTime = MinutesToMilliseconds((long)workTime);
        this.breakTime = MinutesToMilliseconds((long)breakTime);
        this.numCycles = numCycles;
    }

    // TODO: start the Work Cycle and returns whether or not the cycle is finished
    public void startWorkCycle(){

        if(timer == null)
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

                    if(delta >= workTime){
                        delta = (long)workTime;
                        timer.stop();
                    }

                    long remainingTime = (long)workTime - delta;

                    SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                    timeDisplay.setText(df.format(remainingTime));
                }
            });
        }
        timer.setInitialDelay(0);
        timer.start();
    }

    public void startBreakCycle(){

    }

    public void startLongBreakCycle(){

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

    // Hellper function to convert minutes to milliseconds
    private long MinutesToMilliseconds(long minutes){
        return minutes * 60 * 1000;
    }

}
