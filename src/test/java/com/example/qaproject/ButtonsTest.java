package com.example.qaproject;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ButtonsTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();

		driver.navigate().to("https://demoqa.com/elements");
		WebElement menuItem4 = driver.findElement(By.id("item-4"));
		menuItem4.click();

		String URL = driver.getCurrentUrl();
		assertThat(URL).isEqualToIgnoringCase("https://demoqa.com/buttons");
	}

	@Test
	@SneakyThrows
	public void ButtonsTest() {

		//check the Double Click Button
		WebElement doubleClickBtn = driver.findElement(By.id("doubleClickBtn"));

		Actions actions = new Actions(driver);
		actions.doubleClick(doubleClickBtn).perform();

		WebElement doubleClickMessage = driver.findElement(By.id("doubleClickMessage"));
		assertThat(doubleClickMessage.isDisplayed()).isTrue();
		assertThat(doubleClickMessage.getText()).isNotEmpty()
				.isEqualToIgnoringCase("You have done a double click")
				.isNotEqualToIgnoringCase("You have done a dynamic click");

		//check the Right Click Button
		WebElement rightClickBtn = driver.findElement(By.id("rightClickBtn"));
		Actions action = new Actions(driver);
		action.contextClick(rightClickBtn).perform();

		WebElement rightClickMessage = driver.findElement(By.id("rightClickMessage"));
		assertThat(rightClickMessage.isDisplayed()).isTrue();
		assertThat(rightClickMessage.getText()).isNotEmpty()
				.isEqualToIgnoringCase("You have done a right click")
				.isNotEqualToIgnoringCase("You have done a double click");

		//check the Click Button
		WebElement clickBtn = driver.findElement(By.cssSelector("div.col-12.mt-4.col-md-6 > div:nth-child(2) > div:nth-child(3) > button"));
		clickBtn.click();

		WebElement dynamicClickMessage = driver.findElement(By.id("dynamicClickMessage"));
		assertThat(dynamicClickMessage.isDisplayed()).isTrue();
		assertThat(dynamicClickMessage.getText()).isNotEmpty()
				.isEqualToIgnoringCase("You have done a dynamic click")
				.isNotEqualToIgnoringCase("You have done a right click");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
