package selenium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeleniumTest {
    private Selenium test = new Selenium();
    private String url;

    @Test
    public void testUrlWixInvokeBrowser() {
        url = "https://wix.com";
        test.invokeBrowser(url);
        test.closeBrowser();
    }

    @Test
    public void testUrlFlipkartInvokeBrowser() {
        url = "https://flipkart.com";
        test.invokeBrowser(url);
        test.closeBrowser();
    }

    @Test
    public void testFacebookLogin() {
        url = "https://facebook.com";
        test.login(url, "email", "pass");
    }

    @Test
    public void testFacebookGetUserName() {
        url = "https://facebook.com";
        test.login(url, "email", "pass");
        String expected = "userName";
        String actual = test.getUserName("//*[@id=\"u_0_a\"]/div[1]/div[1]/div/a/span/span");
        assertEquals(expected, actual);
    }
}