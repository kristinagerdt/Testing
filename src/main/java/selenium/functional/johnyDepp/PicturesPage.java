package selenium.functional.johnyDepp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selenium.functional.PageObject;

import java.util.List;

public class PicturesPage extends PageObject {
    public PicturesPage(WebDriver driver) {
        super(driver);
    }

    private WebElement linkBilder = null;

    public WebElement searchItem() {
        WebElement container = driver.findElement(By.id("hdtb-msb-vis"));
        List<WebElement> elements = container.findElements(By.tagName("a"));
        for (WebElement item : elements) {
            if (item.getText().equals("Картинки")) {
                linkBilder = item;
                break;
            }
        }
        return linkBilder;
    }

    public void click() {
        linkBilder.click();
    }
}