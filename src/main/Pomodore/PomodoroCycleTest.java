package main.Pomodore;

import main.Pomodore.Pomodore;
import main.Pomodore.PomodoroConfig;
import main.Pomodore.PomodoroCycle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PomodoroCycleTest
{
    Pomodore frame = new Pomodore();
    PomodoroConfig config = new PomodoroConfig(1, 25, 5, 4, 20);
    PomodoroCycle cycle = new PomodoroCycle(frame, config);

    @Test
    void startTimerAndStopAfter1Minute()
    {
        cycle.startWorkCycle();
        assertFalse(cycle.timerPaused);
        assertFalse(cycle.timerReset);
    }

    @Test
    void resetValuesOnTimerReset()
    {
        cycle.startWorkCycle();

        cycle.resetTimer();

        assertTrue(cycle.timerReset);
        assertEquals(1, cycle.getCurNumCycles());
        assert (cycle.getNumCycles() == 1);
    }

    @Test
    void getCycle()
    {
        cycle.startWorkCycle();

        //TODO
        cycle.getCurCycle();
    }

    @Test
    void cycleTransitions()
    {
        PomodoroConfig zeroConfig = new PomodoroConfig(4, 0, 0, 2, 0);
        cycle = new PomodoroCycle(frame, zeroConfig);

        cycle.startWorkCycle();

        assertSame(cycle.getCurCycle(), PomodoroCycle.CycleType.WORK);
    }

    @Test
    void startResetStartCycle()
    {
        cycle.startWorkCycle();
        cycle.resetTimer();
        cycle.startWorkCycle();

        assertFalse(cycle.timerReset);
        assertFalse(cycle.timerPaused);
        assertSame(cycle.getCurCycle(), PomodoroCycle.CycleType.WORK);
    }

    @Test
    void pressStartInBreakCycle()
    {

    }

    @Test
    void startPause()
    {
        cycle.startWorkCycle();
        cycle.pauseTimer();

        assertTrue(cycle.timerPaused);
        assertFalse(cycle.timerReset);
    }

    @Test
    void checkSetConfig()
    {
        PomodoroConfig config = new PomodoroConfig(3, 21, 6, 14, 2);
        cycle.SetConfig(config);

        assertEquals(cycle.getWorkTime(), config.workTime);
        assertEquals(cycle.getBreakTime(), config.breakTime);
        assertEquals(cycle.getBreakTimePomodoro(), config.breakTimePomodoro);
        assertEquals(cycle.getNumCycles(), config.numCycles);
        assertEquals(cycle.getNumCyclesPomodoro(), config.numPomodoroCycles);
    }
}
