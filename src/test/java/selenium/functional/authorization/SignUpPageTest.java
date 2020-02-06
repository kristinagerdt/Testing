package selenium.functional.authorization;

import org.junit.Test;
import selenium.functional.FunctionalTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SignUpPageTest extends FunctionalTest {
    private String url = "https://www.kimschiller.com/page-object-pattern-tutorial/index.html";

    @Test
    public void signUp() throws InterruptedException {
        driver.get(url);
        SignUpPage signUpPage = new SignUpPage(driver);
        assertTrue(signUpPage.isInitialized());

        signUpPage.enterName("firstName", "lastName");
        signUpPage.enterAddress("333 Street", "998877");
        //Thread.sleep(2000); //don't use in production, in production use implicitlyWait()

        ReceiptPage receiptPage = signUpPage.submit();
        assertTrue(receiptPage.isInitialized());
        assertEquals("Thank you!", receiptPage.confirmationHeader());
    }
}