package com.example.qaproject;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class TextBoxTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        File file = new File("src/test/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();

        driver.navigate().to("https://demoqa.com/elements");
        WebElement menuItem0 = driver.findElement(By.id("item-0"));
        menuItem0.click();

        String URL = driver.getCurrentUrl();
        assertThat(URL).isEqualToIgnoringCase("https://demoqa.com/text-box");
    }

    @Test
    @SneakyThrows
    public void textBoxTest() {

        //finding elements
        WebElement userName = driver.findElement(By.id("userName"));
        WebElement currentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/form/div[3]/div[2]/textarea"));
        WebElement permanentAddress = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/form/div[4]/div[2]/textarea"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        //sending keys
        userName.clear();
        currentAddress.clear();
        permanentAddress.clear();
        userName.sendKeys("User Name");
        currentAddress.sendKeys("Current Address");
        permanentAddress.sendKeys("Permanent Address");

        submitBtn.click();

        //checking the results
        WebElement nameOutput = driver.findElement(By.id("name"));
        assertThat(nameOutput.getText()).isNotEmpty()
                .isEqualToIgnoringCase("Name:" + "User Name")
                .isNotEqualToIgnoringCase("Current Address");

        WebElement currentAddressOutput = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/form/div[6]/div/p[2]"));
        assertThat(currentAddressOutput.getText()).isNotEmpty()
                .isEqualToIgnoringCase("Current Address :" + "Current Address")
                .isNotEqualToIgnoringCase("Permanent Address");

        WebElement permanentAddressOutput = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/form/div[6]/div/p[3]"));
        assertThat(permanentAddressOutput.getText()).isNotEmpty()
                .isEqualToIgnoringCase("Permananet Address :" + "Permanent Address")
                .isNotEqualToIgnoringCase("User Name");
    }

    @Test
    public void emailTest() {
        String wrongEmail = "wrong.email";
        String validEmail = "valid.email@test.ua";

        WebElement userEmail = driver.findElement(By.id("userEmail"));
        WebElement submitBtn = driver.findElement(By.id("submit"));

        // wrong email
        userEmail.clear();
        userEmail.sendKeys(wrongEmail);
        submitBtn.click();
        assertThat(userEmail.getAttribute("class")).containsIgnoringCase("field-error");

        // valid email
        userEmail.clear();
        userEmail.sendKeys(validEmail);
        submitBtn.click();
        assertThat(userEmail.getAttribute("class")).doesNotContainIgnoringCase("field-error");

        submitBtn.click();

        WebElement emailOutput = driver.findElement(By.id("email"));
        assertThat(emailOutput.getText()).isNotEmpty()
                .containsIgnoringCase(validEmail)
                .doesNotContainIgnoringCase(wrongEmail);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
