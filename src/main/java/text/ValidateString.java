package text;

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
}