package junit.text;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Transform {
    public static String transform(String sentence,
                                   Predicate<String> predicate,
                                   Function<String, String> function) throws IncorrectInputDataException {
        if (sentence != null) {
            return Arrays.stream(sentence.split(" "))
                    .map(s -> predicate.test(s) ? function.apply(s) : s)
                    .collect(Collectors.joining(" "));
        } else {
            throw new IncorrectInputDataException("Input sentence is null");
        }
    }
}