package text;

import org.junit.Before;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TransformTest {
    private Predicate<String> isFour;
    private Function<String, String> toUpperCase;

    private Predicate<String> isThree;
    private Function<String, String> toLowerCase;

    private String sentence;

    @Before
    public void setUp() throws Exception {
        isFour = s -> s.length() == 4;
        toUpperCase = String::toUpperCase;
        isThree = s -> s.length() == 3;
        toLowerCase = String::toLowerCase;
    }

    // ToUpper()
    @Test
    public void testMultiSentenceToUpper() throws Exception {
        sentence = "abCd aad BBEF aBb Fg";
        String expected = "abCd AAD BBEF ABB Fg";
        String actual = Transform.transform(sentence, isThree, toUpperCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testOneWordSentenceToUpper() throws Exception {
        sentence = "aDa";
        String expected = "ADA";
        String actual = Transform.transform(sentence, isThree, toUpperCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testOneWordSentenceLength5ToUpper() throws Exception {
        sentence = "aDaFf";
        String expected = "aDaFf";
        String actual = Transform.transform(sentence, isThree, toUpperCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testSentenceWithSpacesToUpper() throws Exception {
        sentence = "abCd  aad ";
        String expected = "abCd  AAD";
        String actual = Transform.transform(sentence, isThree, toUpperCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testEmptySentenceToUpper() throws Exception {
        sentence = "";
        String expected = "";
        String actual = Transform.transform(sentence, isThree, toUpperCase);
        assertEquals("Output", expected, actual);
    }

    @Test(expected = IncorrectInputDataException.class)
    public void testNullSentenceToUpper() throws Exception {
        String actual = Transform.transform(null, isThree, toUpperCase);
        assertNull("Output", actual);
    }

    // ToLower()
    @Test
    public void testMultiSentenceToLower() throws Exception {
        sentence = "abCd aad BBEF aBb Fg";
        String expected = "abcd aad bbef aBb Fg";
        String actual = Transform.transform(sentence, isFour, toLowerCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testOneWordSentenceToLower() throws Exception {
        sentence = "aDaF";
        String expected = "adaf";
        String actual = Transform.transform(sentence, isFour, toLowerCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testOneWordSentenceLength5ToLower() throws Exception {
        sentence = "aDaFf";
        String expected = "aDaFf";
        String actual = Transform.transform(sentence, isFour, toLowerCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testSentenceWithSpacesToLower() throws Exception {
        sentence = "abCd  aad ";
        String expected = "abcd  aad";
        String actual = Transform.transform(sentence, isFour, toLowerCase);
        assertEquals("Output", expected, actual);
    }

    @Test
    public void testEmptySentenceToLower() throws Exception {
        sentence = "";
        String expected = "";
        String actual = Transform.transform(sentence, isFour, toLowerCase);
        assertEquals("Output", expected, actual);
    }

    @Test(expected = IncorrectInputDataException.class)
    public void testNullSentenceToLower() throws Exception {
        String actual = Transform.transform(null, isFour, toLowerCase);
        assertNull("Output", actual);
    }
}