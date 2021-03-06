package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class Demo {
    private WebDriver driver;

    public Demo(WebDriver driver) {
        this.driver = driver;
    }

    public void invokeBrowser(String url) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(url);
    }

    public String getTagText(String value) {
        WebElement webElement = driver.findElement(By.xpath(value));
        return webElement.getText();
    }

    public void closeBrowser() {
        driver.close();
    }

    // for facebook
    private void login() {
        driver.findElement(By.id("email")).sendKeys("email");
        driver.findElement(By.id("pass")).sendKeys("pass");
        driver.findElement(By.id("loginbutton")).click();
        //.submit(); //enter

        WebElement userName = driver.findElement(By.xpath("//*[@id=\"u_0_a\"]/div[1]/div[1]/div/a/span/span"));
        //driver.switchTo().activeElement();
        System.out.println(userName.getText());
    }
}