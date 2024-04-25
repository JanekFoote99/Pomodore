package Pomodoro;

import javax.swing.*;

public class PomodoroManager
{
    private PomodoroCycle curPomodoro;
    private PomodoroConfig config;
    private Pomodoro frame;
    private long startTimePomodoro;

    private Timer timer;

    public PomodoroManager(PomodoroConfig config, Pomodoro frame)
    {
        this.config = config;
        this.frame = frame;
    }

    public void pausePomodoro(){
        if(curPomodoro.timerPaused){
            curPomodoro.timerPaused = false;
            curPomodoro.continueTimer();
        }else{
            curPomodoro.timerPaused = true;
            curPomodoro.pauseTimer();
        }
    }

    public void startPomodoro(PomodoroConfig config)
    {
        if (curPomodoro == null)
        {
            curPomodoro = new PomodoroCycle(frame.getTimerStatusText(), config.workTime, config.breakTime, config.numCycles);

            curPomodoro.startWorkCycle();
        }
    }
}
