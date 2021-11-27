package team.emergence._15puzzle.util;

public class MoveCounter {
    private int move;

    public MoveCounter() {
        this.move = 0;
    }

    public int getMove() {
        return move;
    }

    public void count() {
        this.move++;
    }
}
