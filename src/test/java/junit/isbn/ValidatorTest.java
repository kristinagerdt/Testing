package junit.isbn;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//@BeforeClass – Run once before any of the test methods in the class, public static void
//@AfterClass – Run once after all the tests in the class have been run, public static void
//@Before – Run before @Test, public void
//@After – Run after @Test, public void
//@Test – This is the test method to run, public void

public class ValidatorTest {
    private Validator validator = new Validator();

    @Test
    public void testValidIsbnBook1Check() {
        assertTrue(validator.check("0143108271"));
    }

    @Test
    public void testValidIsbnChangeOneDigitCheck() {
        assertFalse(validator.check("0143108272"));
    }

    @Test
    public void testValidIsbnBook2Check() {
        assertTrue(validator.check("0451163966"));
    }

    @Test(expected = NumberFormatException.class)
    public void testOnly10SymbolsAllowedCheck() {
        validator.check("04511639664");
    }

    @Test(expected = NumberFormatException.class)
    public void testOnly10DigitsAllowedCheck() {
        validator.check("0f4511639664");
    }

    @Test
    public void testLastSymbolXCheck() {
        assertTrue(validator.check("012000030X"));
    }

    @Test
    public void testValidIsbn13Check() {
        assertTrue(validator.check("9780120000306"));
    }
}