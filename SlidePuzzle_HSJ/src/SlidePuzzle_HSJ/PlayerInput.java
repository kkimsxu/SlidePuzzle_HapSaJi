package SlidePuzzle_HSJ;

import java.io.*;
import javax.swing.*;
import java.util.StringTokenizer;

public class PlayerInput {
    private static final String FILE_PATH = "/Users/kkimsxu/Desktop/github.kkimsxu/SlidePuzzle_HapSaJi/PlayerInput.txt";
    private static final int LEVELS = 3;


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
    }


    // 기존 플레이어의 정보를 파일에서 불러옴
    private static Player[] loadPlayersFromFile() {
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
    private static Player findPlayerByUsername(String username, Player[] players) {
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


    private static void updatePlayerInfo(Player player) {
        // 기존 플레이어 정보 업데이트
        // 점수 저장같은 거 근데 아마 Player 모델 클래스에서 해야할 거 같음 ㅁㄹ겠어솔직히
    }



}
