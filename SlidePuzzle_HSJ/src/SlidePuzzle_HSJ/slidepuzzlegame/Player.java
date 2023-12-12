package slidepuzzlegame;

public class Player {
    private String username;
    private String password;
    private int level1Score;
    private int level2Score;
    private int level3Score;

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Player(String username, String password, int level1Score, int level2Score, int level3Score) {
        this.username = username;
        this.password = password;
        this.level1Score = level1Score;
        this.level2Score = level2Score;
        this.level3Score = level3Score;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel1Score() {
        return level1Score;
    }

    public int getLevel2Score() {
        return level2Score;
    }

    public int getLevel3Score() {
        return level3Score;
    }

    // Setters for scores
    public void setLevel1Score(int score) {
        if (this.level1Score == 0 || score < this.level1Score) {
            this.level1Score = score;
        }
    }

    public void setLevel2Score(int score) {
        if (this.level2Score == 0 || score < this.level2Score) {
            this.level2Score = score;
        }
    }

    public void setLevel3Score(int score) {
        if (this.level3Score == 0 || score < this.level3Score) {
            this.level3Score = score;
        }
    }

    // Update scores method
    public void updateScores(int newLevel1Score, int newLevel2Score, int newLevel3Score) {
        setLevel1Score(newLevel1Score);
        setLevel2Score(newLevel2Score);
        setLevel3Score(newLevel3Score);
    }
}
