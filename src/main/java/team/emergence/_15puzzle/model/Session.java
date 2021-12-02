package team.emergence._15puzzle.model;

import team.emergence._15puzzle.core.MoveCounter;
import team.emergence._15puzzle.core.Stopwatch;

public class Session {
    private final GameConfig config;
    private MoveCounter counter;
    private Stopwatch stopwatch;

    public Session(GameConfig config) {
        this.config = config;
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

    public void refresh() {
        this.stopwatch = new Stopwatch();
        this.counter = new MoveCounter();
        stopwatch.start();
    }

    public void end() {
        stopwatch.pause();
    }
}
