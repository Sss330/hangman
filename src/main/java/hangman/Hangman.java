package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Hangman {

    protected static int USER_ATTEMPTS = AttemptsToGuess.ATTEMPTS.getAttempts();

    private static final List<String> WORDS = new ArrayList<>();
    private static final Set<Character> GUESSED_LETTERS = new HashSet<>();

    private static String chosenWord;
    private static char[] maskedWord;

    private static final Scanner scanner = new Scanner(System.in);

    public static void startGame(boolean isThatFirstLaunch) {
        if (isThatFirstLaunch) {
            userStartDialog();
        }

        loadWords();
        chooseRandomWord();

        System.out.println("You have " + USER_ATTEMPTS + " attempts to guess the word");

        HangRender.printGallowsByAttempts(USER_ATTEMPTS);
        printMask();

        while (USER_ATTEMPTS > 0) {
            playerGuess();
            printMask();
            HangRender.printGallowsByAttempts(USER_ATTEMPTS);
            System.out.println("Errors: " + (AttemptsToGuess.ATTEMPTS.getAttempts() - USER_ATTEMPTS));
            if (isWordGuessed()) {
                System.out.println("Graz 🎉, you guessed the word");
                break;
            }
        }
        if (USER_ATTEMPTS == 0) {
            System.out.println("You lost");
        }
        System.out.println("Game is over.\nThe word was: " + chosenWord);
        System.out.println("Do you want to play again?[y/n]");
        String userPlayAgainAnswer = scanner.next();
        if (userPlayAgainAnswer.toLowerCase(Locale.ROOT).equals("y")) {
            startNewRound();
        } else {
            System.out.println("Bye");
        }
    }

    private static void userStartDialog() {
        while (true) {
            System.out.println("Do you want to play in Hangman? [y/n]");
            String userAnswer = scanner.next();
            if (userAnswer.toLowerCase(Locale.ENGLISH).equals("y")) {
                return;
            } else if (userAnswer.toLowerCase(Locale.ENGLISH).equals("n")) {
                System.out.println("Bye");
                System.exit(0);
            }

        }
    }

    private static void loadWords() {
        if (!WORDS.isEmpty()) {
            return;
        }

        File filePath = new File("src/main/resources/words.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                WORDS.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }

    private static void chooseRandomWord() {
        Random randomComputer = new Random();
        chosenWord = WORDS.get(randomComputer.nextInt(WORDS.size()));
        maskedWord = new char[chosenWord.length()];
        Arrays.fill(maskedWord, '*');
    }

    private static void printMask() {
        System.out.print("Word: ");
        for (char c : maskedWord) {
            System.out.print(c);
        }
        System.out.print("\nGuessed letters: " + GUESSED_LETTERS + "\n");
    }

    private static void playerGuess() {
        System.out.print("Insert letter: ");
        String userInput = scanner.next();
        while (!isUserInputCorrect(userInput)) {
            System.out.print("Insert letter: ");
            userInput = scanner.next();
        }
        userInput = userInput.toLowerCase(Locale.ENGLISH);
        char guessLetter = userInput.charAt(0);
        boolean isLetterAlreadyInserted = !GUESSED_LETTERS.add(guessLetter);

        boolean isCorrectGuess = false;

        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == guessLetter) {
                maskedWord[i] = guessLetter;
                isCorrectGuess = true;
            }
        }

        if (isCorrectGuess) {
            System.out.println("You guessed right");
        } else {
            if (!isLetterAlreadyInserted) {
                System.out.println("You guessed wrong");
                USER_ATTEMPTS--;
            } else {
                System.out.println("You already inserted: " + guessLetter);
            }
        }
    }

    private static boolean isWordGuessed() {
        return new String(maskedWord).equals(chosenWord);
    }

    private static boolean isUserInputCorrect(String userInput) {
        if (!userInput.matches("[A-Za-z]")) {
            System.out.println("Hangman applies only English letters by a letter");
            return false;
        }
        return true;
    }

    private static void startNewRound() {
        USER_ATTEMPTS = AttemptsToGuess.ATTEMPTS.getAttempts();
        WORDS.clear();
        GUESSED_LETTERS.clear();
        chosenWord = null;
        maskedWord = null;
        startGame(false);
    }
}
