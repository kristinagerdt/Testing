package junit.text;

import java.util.Arrays;

public class ValidateString {
    public static String validateWithCustomException(String input) throws IncorrectInputDataException {
        if (input != null) {
            if (input.length() == 3) return input.toUpperCase();
            if (input.length() == 4) return input.toLowerCase();
            return input;
        } else {
            throw new IncorrectInputDataException("Input is null!");
        }
    }

    public static String validate(String input) {
        if (input == null) return "";
        if (input.length() == 3) return input.toUpperCase();
        if (input.length() == 4) return input.toLowerCase();
        return input;
    }

    public static long getWordCount(String input, String firstChar) {
        if (input != null && firstChar != null) {
            return Arrays
                    .stream(input.split(" "))
                    .filter(s -> s.startsWith(firstChar))
                    .count();
        }
        return 0;
    }

    public static boolean isDigit(String input) {
        if (input != null && input.length() != 0) {
            return input
                    .chars()
                    .allMatch(Character::isDigit);
        }
        return false;
    }
}