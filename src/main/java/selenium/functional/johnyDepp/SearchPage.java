package selenium.functional.johnyDepp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.functional.PageObject;

public class SearchPage extends PageObject {
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "q")
    private WebElement input;

    @FindBy(name = "btnK")
    private WebElement submitButton;

    public void enterQuery(String input) {
        this.input.clear();
        this.input.sendKeys(input);
    }

    public PicturesPage submit() {
        this.submitButton.submit();
        return new PicturesPage(driver);
    }
}