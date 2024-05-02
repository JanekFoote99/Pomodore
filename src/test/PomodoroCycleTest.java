package test;

import Pomodoro.PomodoroConfig;
import Pomodoro.PomodoroCycle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PomodoroCycleTest
{
    PomodoroConfig config = new PomodoroConfig(1, 25, 5, 4, 20);
    @Test
    void startTimerAndStopAfter1Minute(){
        PomodoroCycle cycle = new PomodoroCycle(config);

        cycle.startWorkCycle();
        assertFalse(cycle.timerPaused);
        assertFalse(cycle.timerReset);
    }

    @Test
    void resetValuesOnTimerReset(){

    }

    @Test
    void cycleTransitions(){
        PomodoroCycle cycle = new PomodoroCycle(config);

        cycle.startWorkCycle();
    }
}