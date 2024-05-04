package Pomodoro;

public class PomodoroConfig
{
    public float workTime;
    public float breakTime;

    // Break Time between two Pomodoros
    public float breakTimePomodoro;
    // Number of work/time cycles it takes to complete one Pomodoro
    public int numPomodoroCycles;

    public int numCycles;

    public PomodoroConfig(int numCycles, float workTime, float breakTime, int numCyclesPomodoro, float breakTimePomodoro)
    {
        this.numCycles = numCycles;
        this.workTime = workTime;
        this.breakTime = breakTime;
        this.numPomodoroCycles = numCyclesPomodoro;
        this.breakTimePomodoro = breakTimePomodoro;
    }

    public void SetWorkTime(float time){ workTime = time; }
    public void SetBreakTime(float time){ breakTime = time; }
    public void SetLongBreakTime(float time) { breakTimePomodoro = time; }
    public void SetNumberOfCycles(int num) { numCycles = num; }
    public void SetNumberOfPomodoroCycles(int num) { numPomodoroCycles = num; }
}
