package selenium.functional.drebedengi;

import org.junit.Test;
import selenium.functional.FunctionalTest;

public class InputPageTest extends FunctionalTest {
    private String url = "https://www.drebedengi.ru/";

    @Test
    public void addExpense() {
        driver.get(url);
        StartPage startPage = new StartPage(driver);
        InputPage inputPage = startPage.click();
        inputPage.enterExpense("200", "new shoes");
        inputPage.click();
    }
}