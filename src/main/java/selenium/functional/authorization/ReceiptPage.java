package selenium.functional.authorization;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.functional.PageObject;

public class ReceiptPage extends PageObject {
    public ReceiptPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(tagName = "h1")
    private WebElement header;

    public boolean isInitialized() {
        return header.isDisplayed();
    }

    public String confirmationHeader() {
        return header.getText();
    }
}