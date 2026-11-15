package hangman;

public class HangRender {
    public static void printGallowsByAttempts(int attempts) {
        if (attempts < 0 || attempts > AttemptsToGuess.ATTEMPTS.getAttempts()) {
            throw new IllegalArgumentException("Attempts can't be more that " + AttemptsToGuess.ATTEMPTS + " or less than 0");
        }

        switch (attempts) {
            case 6 -> System.out.println("""
                     ---------
                     |/      |
                     |      \s
                     |
                     |
                     |
                     |__________|
                    \s""");
            case 5 -> System.out.println("""
                    ---------
                    |/      |
                    |       O
                    |
                    |
                    |
                    |__________|
                    """);
            case 4 -> System.out.println("""
                     ---------
                     |/      |
                     |       O
                     |       |
                     |      \s
                     |
                     |__________|
                    \s""");
            case 3 -> System.out.println("""
                     ---------
                     |/      |
                     |       O
                     |      /|
                     |        \s
                     |
                     |__________|
                    \s""");
            case 2 -> System.out.println("""
                    ---------
                    |/      |
                    |       O
                    |      /|\\
                    |
                    |
                    |__________|
                    """);
            case 1 -> System.out.println("""
                    ---------
                    |/      |
                    |       O
                    |      /|\\
                    |      /
                    |
                    |__________|
                    """);
            case 0 -> System.out.println("""
                    ---------
                    |/      |
                    |       O
                    |      /|\\
                    |      / \\
                    |
                    |__________|
                    """);
        }
    }
}
