package selenium.functional.drebedengi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.functional.PageObject;

public class StartPage extends PageObject {
    public StartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "do_reg")
    private WebElement demoButton;

    public InputPage click() {
        this.demoButton.click();
        return new InputPage(driver);
    }
}