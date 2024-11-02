import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest {

    @Test
    public void checkPositiveSignUp() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("[value = Continue]")).click();

        driver.findElement(By.name("first_name")).sendKeys("Timofei");
        driver.findElement(By.name("last_name")).sendKeys("Borodich");
        driver.findElement(By.name("email")).sendKeys("blabla@bla.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("12345678");
        driver.findElement(By.cssSelector("[value = Register]")).click();
        String signUpMassage = driver.findElement(By.className("confirmation_message")).getText();

//        By locator = By.className("confirmation_message"); строчка выше состоит из этих трех строк
//        WebElement confirmMassage = driver.findElement(locator);
//        String signUpMessage1= confirmMassage.getText();

        Assert.assertEquals(signUpMassage,"Account is created!");

        driver.quit();
    }
    @Test
    public void checkNegativeSignUpPassword2() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        driver.findElement(By.name("zip_code")).sendKeys("11111");
        driver.findElement(By.cssSelector("[value = Continue]")).click();
        driver.findElement(By.name("first_name")).sendKeys("Timofei");
        driver.findElement(By.name("last_name")).sendKeys("Borodich");
        driver.findElement(By.name("email")).sendKeys("blabla@bla.com");
        driver.findElement(By.name("password1")).sendKeys("12345678");
        driver.findElement(By.name("password2")).sendKeys("1234567");
        driver.findElement(By.cssSelector("[value = Register]")).click();
        String signUpMassage = driver.findElement(By.className("cerror_message")).getText();
        Assert.assertEquals(signUpMassage, "Oops, error on page. Some of your fields have invalid data or email was previously used");
        driver.quit();
    }
}
