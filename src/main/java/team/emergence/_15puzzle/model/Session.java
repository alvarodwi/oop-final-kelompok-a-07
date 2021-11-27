package team.emergence._15puzzle.model;

import team.emergence._15puzzle.util.MoveCounter;
import team.emergence._15puzzle.util.Stopwatch;

public class Session {
    private final GameConfig config;
    private final MoveCounter counter;
    private final Stopwatch stopwatch;

    public Session(int difficulty, String filepath) {
        this.config = new GameConfig(difficulty, filepath);
        this.counter = new MoveCounter();
        this.stopwatch = new Stopwatch();

        stopwatch.start();
    }

    public GameConfig getConfig() {
        return config;
    }

    public MoveCounter getCounter() {
        return counter;
    }

    public Stopwatch getStopwatch() {
        return stopwatch;
    }

    public void endSession() {
        stopwatch.stop();
    }
}
