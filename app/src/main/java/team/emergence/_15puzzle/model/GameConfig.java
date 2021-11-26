package team.emergence._15puzzle.model;

public class GameConfig {
    private int difficulty;
    private String filePath;
    private double tileSize;
    private boolean isPaused = false;

    public GameConfig(int difficulty, String filePath) {
        this.difficulty = difficulty;
        this.filePath = filePath;
        this.tileSize = 600.0 / difficulty;
    }

    public GameConfig(int difficulty, String filePath, double tileSize) {
        this.difficulty = difficulty;
        this.filePath = filePath;
        this.tileSize = tileSize;
        this.tileSize = 600.0 / difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getTileSize() {
        return tileSize;
    }

    public void setTileSize(double tileSize) {
        this.tileSize = tileSize;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}

