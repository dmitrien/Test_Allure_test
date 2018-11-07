package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver)
    {
        super(driver);
    }

    public void login(String username, String password) {
        driver.get("http://localhost/addressbook/index.php");
        type(By.name("user"), username);
        driver.findElement(By.id("LoginForm")).click();
        type (By.name("pass"), password);
        click(By.xpath("//input[@value='Login']"));
    }
}
