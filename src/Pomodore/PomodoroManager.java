package Pomodore;

public class PomodoroManager
{
    private PomodoroCycle curPomodoro;
    private final Pomodore frame;
    private long startTimePomodoro;

    public PomodoroManager(PomodoroConfig config, Pomodore frame)
    {
        this.frame = frame;
        this.curPomodoro = new PomodoroCycle(frame, config);
    }

    public void pausePomodoro()
    {
        if (curPomodoro.timerPaused)
        {
            curPomodoro.timerPaused = false;
            curPomodoro.continueTimer();
        } else
        {
            curPomodoro.timerPaused = true;
            curPomodoro.pauseTimer();
        }
    }

    public void resetPomodoro()
    {
        curPomodoro.resetTimer();
    }

    public void startPomodoro(PomodoroConfig config)
    {
        boolean parameterChanges = false;

        if(curPomodoro.getWorkTime() != config.workTime){
            curPomodoro.setWorkTime(config.workTime);
            parameterChanges = true;
        }
        if(curPomodoro.getBreakTime() != config.breakTime){
            curPomodoro.setBreakTime(config.breakTime);
            parameterChanges = true;
        }
        if(curPomodoro.getBreakTimePomodoro() != config.breakTimePomodoro){
            curPomodoro.setBreakTimePomodoro(config.breakTimePomodoro);
            parameterChanges = true;
        }
        if(curPomodoro.getNumCycles() != config.numCycles){
            curPomodoro.setNumCycles(config.numCycles);
            parameterChanges = true;
        }
        if(curPomodoro.getNumCyclesPomodoro() != config.numPomodoroCycles){
            curPomodoro.setNumCyclesPomodoro(config.numPomodoroCycles);
            parameterChanges = true;
        }

        // Error: for some reason the timer starts in the break Cycle
        if(parameterChanges){
            curPomodoro.resetTimer();
        }

        curPomodoro.startWorkCycle();
    }

    public void SetConfig(PomodoroConfig config)
    {
        curPomodoro.SetConfig(config);
    }
}
