package selenium.functional.drebedengi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import selenium.functional.PageObject;

public class InputPage extends PageObject {
    public InputPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "sum_tab_waste")
    private WebElement expenseInput;

    @FindBy(id = "w_comment")
    private WebElement commentInput;

    @FindBy(id = "w_but")
    private WebElement clickButton;

    private WebElement categoryExpense = null;

    public void enterExpense(String sum, String comment) {
        this.expenseInput.clear();
        this.expenseInput.sendKeys(sum);

        this.commentInput.clear();
        this.commentInput.sendKeys(comment);
    }

    public InputPage click() {
        this.clickButton.click();
        return new InputPage(driver);
    }
}