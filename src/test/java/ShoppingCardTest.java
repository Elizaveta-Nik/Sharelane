import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ShoppingCardTest {

    @Test
    public void checkDiscount0(){
        //регистрация - логинимся - выбираем книгу и отправляем в корзину - заход в корзину и проверяем скидку
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=2&zip_code=12345&first_name=test&last_name=" +
                "test&email=user%40pflb.ru&password1=12345678&password2=12345678");
        String email = driver.findElement(By.xpath(
                "//table/tbody/tr[6]/td/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/b")).getText();

        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value = Login]")).click();

        driver.get("https://www.sharelane.com/cgi-bin/add_to_cart.py?book_id=5");
        driver.get("https://www.sharelane.com/cgi-bin/shopping_cart.py");

        driver.findElement(By.name("q")).clear();
        driver.findElement(By.name("q")).sendKeys("19");
        driver.findElement(By.cssSelector("[value = Update]")).click();

        String discountPercent = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[5]/p/b")).getText();
        String discount$ = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[6]")).getText();
        String total = driver.findElement(By.xpath(
                "/html/body/center/table/tbody/tr[6]/td/table/tbody/tr[2]/td[7]")).getText();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(discountPercent,
                "0",
                "процент дисконта не равен 0");//+сообщение об ошибке
        softAssert.assertEquals(discount$,
                "0.0",
                "сумма дисконта не равна 0");
        softAssert.assertEquals(total,
                "190.0",
                "общая сумма стоимости книг не равна 190");
        driver.quit();
        softAssert.assertAll();
    }
}
