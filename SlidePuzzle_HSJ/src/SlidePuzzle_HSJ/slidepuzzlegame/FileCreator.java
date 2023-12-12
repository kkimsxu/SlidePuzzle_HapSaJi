package slidepuzzlegame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// 이거 그냥 처음에 파일 만들려고 만들어둔 거임
// 게임에는 쓸모없는 클래스입니다~~*^^*
// 근데 일단 그냥 둘게연 혹시 몰라서

public class FileCreator {
    public static void main(String[] args) {
        String filePath = "PlayerInput.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("kkimsxu 0000 488 0 0");
            writer.newLine();
            writer.write("choi 0000 243 299 844");

            System.out.println("File created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to the file.");
        }
    }
}
