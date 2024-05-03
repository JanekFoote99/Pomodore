package Pomodoro;

import javax.swing.*;

public class PomodoroManager
{
    private PomodoroCycle curPomodoro;
    private final Pomodoro frame;
    private long startTimePomodoro;

    public PomodoroManager(PomodoroConfig config, Pomodoro frame)
    {
        this.frame = frame;
        this.curPomodoro = new PomodoroCycle(frame, config);
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

    public void resetPomodoro(){
        curPomodoro.resetTimer();
    }

    public void startPomodoro(PomodoroConfig config)
    {
        if (curPomodoro == null)
        {
            curPomodoro = new PomodoroCycle(frame, config.workTime, config.breakTime, config.numCycles);

            curPomodoro.startWorkCycle();
        } else{
            curPomodoro.SetWorkTime(config.workTime);
            curPomodoro.SetBreakTime(config.breakTime);
            curPomodoro.SetBreakTimePomodoro(config.breakTimePomodoro);
            curPomodoro.SetNumCyclesPomodoro(config.numCyclesPomodoro);
            curPomodoro.SetNumCycles(config.numCycles);

            curPomodoro.startWorkCycle();
        }
    }
}
