package hangman;

public enum AttemptsToGuess {
    ATTEMPTS(6);
    private final int attempts;

    AttemptsToGuess(int attempts) {
        this.attempts = attempts;
    }

    public int getAttempts() {
        return attempts;
    }
}
