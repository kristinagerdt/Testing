package selenium;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoTest {
    private static WebDriver driver;
    private String url;
    private Demo test = new Demo(driver);

    @BeforeClass
    public static void setUp() {
        // https://chromedriver.chromium.org/downloads
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

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
    public void testUrlFacebookInvokeBrowserGetTagText() {
        url = "https://www.facebook.com/";
        test.invokeBrowser(url);
        String tagText = test.getTagText("//*[@id=\"content\"]/div/div/div/div/div[1]/div/div");
        System.out.println(tagText);
        test.closeBrowser();
    }
}