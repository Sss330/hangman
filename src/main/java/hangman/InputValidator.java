package hangman;

public class InputValidator {

    public boolean isUserInputValid(String userInput) {
        if (userInput != null && userInput.matches("[A-Za-z]")) {
            return true;
        } else {
            System.out.println("Hangman applies only English letters by a letter");
            return false;
        }
    }
}
