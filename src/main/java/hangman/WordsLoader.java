package hangman;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class WordsLoader {

    public void loadWords(List<String> words, String path) {
        InputStream inputStream = Hangman.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            try (Scanner fileScanner = new Scanner(inputStream)) {
                while (fileScanner.hasNextLine()) {
                    words.add(fileScanner.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(0);
            }
        }
    }
}
