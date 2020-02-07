package junit.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ValidateStringTest {
    private String input;

    // validateWithCustomException()
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
    public void testNullInputWithoutAssertValidateWithCustomException() throws Exception {
        ValidateString.validateWithCustomException(null);
    }

    // validate()
    @Test
    public void testInputLength3Validate() {
        input = "Era";
        String expected = "ERA";
        String actual = ValidateString.validate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInputLength4Validate() {
        input = "TeMp";
        String expected = "temp";
        String actual = ValidateString.validate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInputLength6Validate() {
        input = "Spring";
        String expected = "Spring";
        String actual = ValidateString.validate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyInputValidate() {
        input = "";
        String expected = "";
        String actual = ValidateString.validate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputValidate() {
        String expected = "";
        String actual = ValidateString.validate(null);
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputWithoutAssertValidate() {
        ValidateString.validate(null);
    }

    // getWordCount()
    @Test
    public void testMultiInputGetWordCount() {
        input = "Every hunter wants to know where the pheasant sits";
        long expected = 2;
        long actual = ValidateString.getWordCount(input, "w");
        assertEquals(expected, actual);
    }

    @Test
    public void testMultiInputEmptyFirstCharGetWordCount() {
        input = "Every hunter wants to know where the pheasant sits";
        long expected = 9;
        long actual = ValidateString.getWordCount(input, "");
        assertEquals(expected, actual);
    }

    @Test
    public void testMultiInputSpaceFirstCharGetWordCount() {
        input = "Every hunter wants to know where the pheasant sits";
        long expected = 0;
        long actual = ValidateString.getWordCount(input, " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testOneWordInputGetWordCount() {
        input = "Every";
        long expected = 0;
        long actual = ValidateString.getWordCount(input, "w");
        assertEquals(expected, actual);
    }

    @Test
    public void testOneWordInputEmptyFirstCharGetWordCount() {
        input = "Every";
        long expected = 1;
        long actual = ValidateString.getWordCount(input, "");
        assertEquals(expected, actual);
    }

    @Test
    public void testOneWordInputSpaceFirstCharGetWordCount() {
        input = "Every";
        long expected = 0;
        long actual = ValidateString.getWordCount(input, " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyInputGetWordCount() {
        input = "";
        long expected = 0;
        long actual = ValidateString.getWordCount(input, "w");
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyInputEmptyFirstCharGetWordCount() {
        input = "";
        long expected = 1;
        long actual = ValidateString.getWordCount(input, "");
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyInputSpaceFirstCharGetWordCount() {
        input = "";
        long expected = 0;
        long actual = ValidateString.getWordCount(input, " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputGetWordCount() {
        long expected = 0;
        long actual = ValidateString.getWordCount(null, "w");
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputEmptyFirstCharGetWordCount() {
        long expected = 0;
        long actual = ValidateString.getWordCount(null, "");
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputSpaceFirstCharGetWordCount() {
        long expected = 0;
        long actual = ValidateString.getWordCount(null, " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testNullInputNullFirstCharGetWordCount() {
        long expected = 0;
        long actual = ValidateString.getWordCount(null, null);
        assertEquals(expected, actual);
    }

    // isDigit()
    @Test
    public void testStringAsNumberIsDigit() {
        boolean actual = ValidateString.isDigit("24");
        assertTrue(actual);
    }

    @Test
    public void testStringAsStringIsDigit() {
        boolean actual = ValidateString.isDigit("2,4");
        assertFalse(actual);
    }

    @Test
    public void testStringAsFractionalNumberIsDigit() {
        boolean actual = ValidateString.isDigit("2.4");
        assertFalse(actual);
    }

    @Test
    public void testEmptyStringIsDigit() {
        boolean actual = ValidateString.isDigit("");
        assertFalse(actual);
    }

    @Test
    public void testNullStringIsDigit() {
        ValidateString.isDigit(null);
    }
}