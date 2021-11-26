package team.emergence._15puzzle.model;

import team.emergence._15puzzle.util.MoveCounter;
import team.emergence._15puzzle.util.Stopwatch;

public class Session {
    private GameConfig config;
    private MoveCounter counter;
    private Stopwatch stopwatch;

    public void startSession(int difficulty, String filepath) {
        this.config = new GameConfig(difficulty,filepath);
        this.counter =  new MoveCounter(); // set 0
        this.stopwatch = new Stopwatch();

        stopwatch.start();
    }

    public void endSession() {
        stopwatch.stop();
    }
}
