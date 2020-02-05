package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Selenium {
    // https://chromedriver.chromium.org/downloads
    private WebDriver driver;

    public Selenium() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void invokeBrowser(String url) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.get(url);
    }

    public void login(String url, String email, String pass) {
        invokeBrowser(url);
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("pass")).sendKeys(pass);
        driver.findElement(By.id("loginbutton")).click();

        //.submit(); //enter
        //driver.switchTo().activeElement();
    }

    public String getUserName(String value) {
        WebElement userName = driver.findElement(By.xpath(value));
        return userName.getText();
    }

    public void closeBrowser() {
        driver.close();
    }
}