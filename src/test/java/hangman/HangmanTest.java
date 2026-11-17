package hangman;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HangmanTest {

    private final InputValidator inputValidator = new InputValidator();
    private final WordsLoader wordsLoader = new WordsLoader();

    @Test
    void singleEnglishLetter_isValid() {
        boolean result = inputValidator.isUserInputValid("a");

        assertTrue(result);
    }

    @Test
    void severalLetters_areInvalid() {
        boolean result = inputValidator.isUserInputValid("abc");

        assertFalse(result);
    }

    @Test
    void null_isInvalid() {
        boolean result = inputValidator.isUserInputValid(null);

        assertFalse(result);
    }

    @Test
    void wordsFile_loadsWords() {
        List<String> words = new ArrayList<>();

        wordsLoader.loadWords(words, "words.txt");

        assertFalse(words.isEmpty());
        assertTrue(words.stream().allMatch(word -> word.matches("[A-Za-z]+")));
    }

    @Test
    void missingFile_doesNotAddWords() {
        List<String> words = new ArrayList<>();

        wordsLoader.loadWords(words, "2345ке");

        assertEquals(0, words.size());
    }
}
