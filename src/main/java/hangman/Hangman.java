package hangman;

import java.util.ArrayList;
import java.util.Collections;
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
    private static final String PATH = "words.txt";

    private static String chosenWord;
    private static List<Character> maskedWord;

    private static final Scanner scanner = new Scanner(System.in);
    private final static InputValidator validator = new InputValidator();
    private final static WordsLoader loader = new WordsLoader();

    public static void startGame(boolean isThatFirstLaunch) {
        if (isThatFirstLaunch) {
            userStartDialog();
        }

        loader.loadWords(WORDS, PATH);
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
        System.out.println("Do you want to play again?[y/n]");
        System.out.println("Game is over.\nThe word was: " + chosenWord);
        String userPlayAgainAnswer = scanner.next();
        if (userPlayAgainAnswer.toLowerCase(Locale.ROOT).equals("y")) {
            clearContextAndStartNewRound();
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

    private static void chooseRandomWord() {
        Random randomComputer = new Random();
        chosenWord = WORDS.get(randomComputer.nextInt(WORDS.size()));
        maskedWord = new ArrayList<>(Collections.nCopies(chosenWord.length(), '*'));
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
        while (!validator.isUserInputValid(userInput)) {
            System.out.print("Insert letter: ");
            userInput = scanner.next();
        }
        userInput = userInput.toLowerCase(Locale.ENGLISH);
        char guessLetter = userInput.charAt(0);
        boolean isLetterAlreadyInserted = !GUESSED_LETTERS.add(guessLetter);

        boolean isCorrectGuess = false;

        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == guessLetter) {
                maskedWord.set(i, guessLetter);
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
                System.out.println("You already insert: " + guessLetter);
            }
        }
    }

    private static void clearContextAndStartNewRound() {
        USER_ATTEMPTS = AttemptsToGuess.ATTEMPTS.getAttempts();
        WORDS.clear();
        GUESSED_LETTERS.clear();
        chosenWord = null;
        maskedWord = null;
        startGame(false);
    }

    private static boolean isWordGuessed() {
        StringBuilder result = new StringBuilder();
        for (Character c : maskedWord) {
            result.append(c);
        }
        return result.toString().equals(chosenWord);
    }
}
