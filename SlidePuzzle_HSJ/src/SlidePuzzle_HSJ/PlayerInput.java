package SlidePuzzle_HSJ;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerInput {
    private static final String FILE_PATH = "C:/Git/SlidePuzzle_HapSaJi/SlidePuzzle_HSJ/PlayerInput.txt";
    private static final int LEVELS = 3;
    private String username;


    public PlayerInput() {
        String username = JOptionPane.showInputDialog(null, "Enter your username:");
        String password = JOptionPane.showInputDialog(null, "Enter your password:");

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            // 기존 플레이어들의 정보를 불러옴
            Player[] players = loadPlayersFromFile();
            // 현재 플레이어의 기록이 있는지 찾아봄
            Player currentPlayer = findPlayerByUsername(username, players);

            // 기록이 있다면
            if (currentPlayer != null) {
                // 비밀번호가 일치하는지 확인
                boolean passwordMatch = false;
                while (!passwordMatch) {
                    if (password != null && currentPlayer.getPassword().equals(password)) {
                        passwordMatch = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Try again.");
                        password = JOptionPane.showInputDialog(null, "Enter your password:");
                    }
                }
            // 기록이 없다면 새로운 플레이어 생성
            } else {
                currentPlayer = new Player(username, password);
                addPlayerToFile(currentPlayer);
            }

        }
        this.username = username; // 사용자 이름 저장
    }

    public String getUsername() {
        return this.username;
    }

    // 기존 플레이어의 정보를 파일에서 불러옴
    public static Player[] loadPlayersFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            int lineCount = 0;

            // 파일의 라인 수 계산
            while (br.readLine() != null) {
                lineCount++;
            }

            // BufferedReader를 다시 열어서 파일을 처음부터 읽음
            br.close();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));

            Player[] players = new Player[lineCount];

            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer t = new StringTokenizer(line, " ");
                String username = t.nextToken().trim();
                String password = t.nextToken();
                int level1Score = Integer.parseInt(t.nextToken());
                int level2Score = Integer.parseInt(t.nextToken());
                int level3Score = Integer.parseInt(t.nextToken());

                players[i] = new Player(username, password, level1Score, level2Score, level3Score);
                i++;
            }
            br.close();
            return players;

        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + FILE_PATH);
            throw new RuntimeException(e.toString());
        }
    }


    // 배열에서 플레이어 찾기
    public static Player findPlayerByUsername(String username, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (username.equals(players[i].getUsername())) {
                return players[i];
            }
        }
        return null;
    }

    // 새로운 플레이어라면 파일에 추가
    private static void addPlayerToFile(Player player) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            // 파일에 새로운 플레이어 정보 추가
            writer.println("\n" + player.getUsername() + " " + player.getPassword() + " 0 0 0");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void updatePlayerInfo(Player updatedPlayer) {
        Player[] playersArray = loadPlayersFromFile(); // 기존 플레이어 목록 불러오기
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(playersArray)); // 배열을 ArrayList로 변환

        // 파일을 다시 쓰기 위해 열기
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (Player player : players) {
                if (player.getUsername().equals(updatedPlayer.getUsername())) {
                    // 수정된 플레이어 정보로 업데이트
                    writer.println(updatedPlayer.getUsername() + " " + updatedPlayer.getPassword() + " "
                            + updatedPlayer.getLevel1Score() + " " + updatedPlayer.getLevel2Score() + " "
                            + updatedPlayer.getLevel3Score());
                } else {
                    // 기존 플레이어 정보 유지
                    writer.println(player.getUsername() + " " + player.getPassword() + " "
                            + player.getLevel1Score() + " " + player.getLevel2Score() + " "
                            + player.getLevel3Score());
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating player info: " + e.getMessage());
        }
    }

    public static List<Player> getRanking(int size) {
        Player[] players = loadPlayersFromFile();
        if (players == null) return new ArrayList<>();

        Comparator<Player> comparator;
        switch (size) {
            case 3:
                comparator = Comparator.comparingInt(Player::getLevel1Score);
                break;
            case 4:
                comparator = Comparator.comparingInt(Player::getLevel2Score);
                break;
            case 5:
                comparator = Comparator.comparingInt(Player::getLevel3Score);
                break;
            default:
                return new ArrayList<>();
        }

        return Arrays.stream(players)
                .filter(p -> {
                    switch (size) {
                        case 3: return p.getLevel1Score() > 0;
                        case 4: return p.getLevel2Score() > 0;
                        case 5: return p.getLevel3Score() > 0;
                        default: return false;
                    }
                })
                .sorted(comparator)
                .collect(Collectors.toList());
    }


}
