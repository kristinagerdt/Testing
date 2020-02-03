package text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ValidateStringTest {
    private String input;

    @Test
    public void testInputLength3ValidateWithCustomException() throws Exception {
        input = "Era";
        String expected = "ERA";
        String actual = ValidateString.validateWithCustomException(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInputLength4ValidateWithCustomException() throws Exception {
        input = "TeMp";
        String expected = "temp";
        String actual = ValidateString.validateWithCustomException(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInputLength6ValidateWithCustomException() throws Exception {
        input = "Spring";
        String expected = "Spring";
        String actual = ValidateString.validateWithCustomException(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyInputValidateWithCustomException() throws Exception {
        input = "";
        String expected = "";
        String actual = ValidateString.validateWithCustomException(input);
        assertEquals(expected, actual);
    }

    @Test(expected = IncorrectInputDataException.class)
    public void testNullInputValidateWithCustomException() throws Exception {
        String actual = ValidateString.validateWithCustomException(null);
        assertNull(actual);
    }

    @Test(expected = IncorrectInputDataException.class)
    public void testNullWithoutAssertInputValidateWithCustomException() throws Exception {
        ValidateString.validateWithCustomException(null);
    }
}