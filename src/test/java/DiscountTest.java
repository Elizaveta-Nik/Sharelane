import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DiscountTest {

    WebDriver driver = new ChromeDriver();

    @AfterTest
    public void driverOut() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String register() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=2&zip_code=12345&first_name=test&last_name=" +
                "test&email=user%40pflb.ru&password1=12345678&password2=12345678");
        String email = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();
        return email;
    }

    private void logIn(String email) {
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value = Login]")).click();
    }

    private void addBookToShoppingCart(WebDriver driver, String quantity) {
        driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=5");
        driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");
        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys(quantity);
        driver.findElement(By.cssSelector("[value = Update]")).click();
    }

    private void discountPercent(SoftAssert softAssert, String expectedDiscountPercent, String expectedDiscount$, String expectedTotal) {
        String discountPercent = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[5]/p/b")).getText();
        String discount$ = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[6]")).getText();
        String total = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[7]")).getText();

        softAssert.assertEquals(discountPercent, expectedDiscountPercent, "Процент дисконта не равен " +
                expectedDiscountPercent);//сравнения фактического значения с ожидаемым
        softAssert.assertEquals(discount$, expectedDiscount$, "Сумма дисконта не равна " + expectedDiscount$);
        softAssert.assertEquals(total, expectedTotal, "Общая сумма стоимости книг не равна " + expectedTotal);
    }

    @Test
    public void checkDiscount0() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "19");
        discountPercent(softAssert, "0", "0.0", "190.0");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount2() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "20");
        discountPercent(softAssert, "2", "4.0", "196.0");
        addBookToShoppingCart(driver, "49");
        discountPercent(softAssert, "2", "9.8", "480.2");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount3() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "50");
        discountPercent(softAssert, "3", "15.0", "485.0");
        addBookToShoppingCart(driver, "99");
        discountPercent(softAssert, "3", "29.7", "960.3");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount4() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "100");
        discountPercent(softAssert, "4", "40.0", "960.0");
        addBookToShoppingCart(driver, "499");
        discountPercent(softAssert, "4", "199.6", "4790.4");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount5() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "500");
        discountPercent(softAssert, "5", "250.0", "4750.0");
        addBookToShoppingCart(driver, "999");
        discountPercent(softAssert, "5", "499.5", "9490.5");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount6() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "1000");
        discountPercent(softAssert, "6", "600.0", "9400.0");
        addBookToShoppingCart(driver, "4999");
        discountPercent(softAssert, "6", "2999.4", "46990.6");

        softAssert.assertAll();
    }

    @Test
    public void checkDiscount7() {
        String email = register();
        logIn(email);
        SoftAssert softAssert = new SoftAssert();

        addBookToShoppingCart(driver, "5000");
        discountPercent(softAssert, "7", "3500.0", "46500.0");
        addBookToShoppingCart(driver, "9999");
        discountPercent(softAssert, "7", "6999.3", "92999.7");

        softAssert.assertAll();
    }
}

