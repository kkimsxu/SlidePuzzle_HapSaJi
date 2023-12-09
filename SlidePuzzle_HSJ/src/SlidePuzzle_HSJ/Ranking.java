package SlidePuzzle_HSJ;

import java.io.*;
import java.util.*;

class Ranking {
    private static final String RANKING_FILE = "ranking.txt";

    public static void saveRanking(String playerName, int size, long time) {
        String record = playerName + "," + size + "," + time + "\n";

        try (FileWriter fw = new FileWriter(RANKING_FILE, true)) {
            fw.write(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<String[]> getRanking(int size) {
        List<String[]> rankingRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RANKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && Integer.parseInt(data[1]) == size) {
                    rankingRecords.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 완료 시간에 따라 랭킹 정렬
        rankingRecords.sort((a, b) -> Long.compare(Long.parseLong(a[2]), Long.parseLong(b[2])));

        return rankingRecords;
    }
}

