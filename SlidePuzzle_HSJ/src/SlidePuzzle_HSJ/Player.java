package SlidePuzzle_HSJ;

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

    public Player(String username, String password, int level1Score, int level2Score, int level3Score){
        this.username = username;
        this.password = password;
        this.level1Score = level1Score;
        this.level2Score = level2Score;
        this.level3Score = level3Score;
    }

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
}
