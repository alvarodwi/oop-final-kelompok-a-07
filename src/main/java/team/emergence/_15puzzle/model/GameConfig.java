package team.emergence._15puzzle.model;

public class GameConfig {
    private final int difficulty;
    private final String filePath;
    private final boolean useSample;
    private final double tileSize;

    public GameConfig(int difficulty, String filePath, boolean useSample) {
        this.difficulty = difficulty;
        this.filePath = filePath;
        this.useSample = useSample;
        this.tileSize = 600.0 / difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getFilePath() {
        return filePath;
    }

    public double getTileSize() {
        return tileSize;
    }

    @Override
    public String toString() {
        return String.format("diff > %d\npath > %s\n", difficulty,filePath);
    }
}

