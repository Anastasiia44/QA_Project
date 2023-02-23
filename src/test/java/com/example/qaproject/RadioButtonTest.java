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

public class RadioButtonTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();

		driver.navigate().to("https://demoqa.com/elements");
		WebElement menuItem2 = driver.findElement(By.id("item-2"));
		menuItem2.click();

		String URL = driver.getCurrentUrl();
		assertThat(URL).isEqualToIgnoringCase("https://demoqa.com/radio-button");
	}

	@Test
	@SneakyThrows
	public void radioButtonTest() {

		//check 'yes' option
		WebElement yesRadio = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/label"));

		boolean isSelected = yesRadio.isSelected();


		if(!isSelected) {
			yesRadio.click();
		}
		WebElement yesResult = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/p/span"));
		assertThat(yesResult.getText()).isEqualToIgnoringCase("yes");

		WebElement impressiveRadio = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/label"));

		impressiveRadio.click();

		//check 'impressive' option
		WebElement impressiveResult = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/p/span"));
		assertThat(impressiveResult.getText()).isNotEqualToIgnoringCase("yes")
				.isEqualToIgnoringCase("impressive")
				.isNotEmpty();
		assertThat(yesResult.isSelected()).isFalse();

		//check 'no' option
		WebElement noResult = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[4]/label"));
		assertThat(noResult.getText()).isEqualToIgnoringCase("no");
		noResult.click();
		assertThat(noResult.isSelected()).isFalse();
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
