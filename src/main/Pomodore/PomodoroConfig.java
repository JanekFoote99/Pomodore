package main.Pomodore;

public class PomodoroConfig
{
    public float workTime;
    public float breakTime;

    // Break Time between two Pomodoros
    public float breakTimePomodoro;
    // Number of work/time cycles it takes to complete one Pomodoro
    public int numPomodoroCycles;

    public int numCycles;

    public PomodoroConfig(){
        this.workTime = 25;
        this.breakTime = 5;
        this.breakTimePomodoro = 20;
        this.numCycles = 4;
        this.numPomodoroCycles = 1;
    }

    public PomodoroConfig(int numCycles, float workTime, float breakTime, int numCyclesPomodoro, float breakTimePomodoro)
    {
        this.numCycles = numCycles;
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.numPomodoroCycles = numCyclesPomodoro;
        this.breakTimePomodoro = breakTimePomodoro;
    }

    public void update(int numCycles, float workTime, float breakTime, int numCyclesPomodoro, float breakTimePomodoro)
    {
        this.numCycles = numCycles;
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.numPomodoroCycles = numCyclesPomodoro;
        this.breakTimePomodoro = breakTimePomodoro;
    }

    public void update(PomodoroConfig config)
    {
        this.numCycles = config.numCycles;
        this.workTime = config.workTime;
        this.breakTime = config.breakTime;
        this.numPomodoroCycles = config.numPomodoroCycles;
        this.breakTimePomodoro = config.breakTimePomodoro;
    }

    public void SetWorkTime(float time){ workTime = time; }
    public void SetBreakTime(float time){ breakTime = time; }
    public void SetLongBreakTime(float time) { breakTimePomodoro = time; }
    public void SetNumberOfCycles(int num) { numCycles = num; }
    public void SetNumberOfPomodoroCycles(int num) { numPomodoroCycles = num; }
}
