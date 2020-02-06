package selenium.functional.johnyDepp;

import org.junit.Test;
import selenium.functional.FunctionalTest;

public class SearchPageTest extends FunctionalTest {
    private String url = "https://www.google.com";

    @Test
    public void search() {
        driver.get(url);
        SearchPage searchPage = new SearchPage(driver);
        searchPage.enterQuery("johnny depp movies");

        PicturesPage picturesPage = searchPage.submit();
        picturesPage.searchItem();
        picturesPage.click();
    }
}