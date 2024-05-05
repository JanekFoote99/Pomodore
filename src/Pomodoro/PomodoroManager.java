package Pomodoro;

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
            curPomodoro.SetNumCyclesPomodoro(config.numPomodoroCycles);
            curPomodoro.SetNumCycles(config.numCycles);

            // Error: for some reason the timer starts in the break Cycle
            //curPomodoro.resetTimer();
            curPomodoro.startWorkCycle();
        }
    }

    public void SetConfig(PomodoroConfig config){
        curPomodoro.SetConfig(config);
    }
}
