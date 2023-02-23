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

public class CheckBoxTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        File file = new File("src/test/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();

        driver.navigate().to("https://demoqa.com/elements");
        WebElement menuItem1 = driver.findElement(By.id("item-1"));
        menuItem1.click();

        String URL = driver.getCurrentUrl();
        assertThat(URL).isEqualToIgnoringCase("https://demoqa.com/checkbox");
    }

    @Test
    @SneakyThrows
    public void checkBoxTest() {
        WebElement homeCheckbox = driver.findElement(By.xpath("//*[@id=\"tree-node-home\"]"));
        WebElement homeLabel = homeCheckbox.findElement(By.xpath("./.."));
        WebElement homeToggle = homeLabel.findElement(By.xpath("./../button"));

        WebElement expandAllBtn = driver.findElement(By.xpath("//*[@id=\"tree-node\"]/div/button[1]"));
        WebElement collapseAllBtn = driver.findElement(By.xpath("//*[@id=\"tree-node\"]/div/button[2]"));

        // check if 1st layer is collapsed
        Boolean desktopCheckBoxPresent = driver.findElements(By.xpath("//*[@id=\"tree-node-desktop\"]")).size() > 0;
        assertThat(desktopCheckBoxPresent).isFalse();

        // expand Home node
        homeToggle.click();

        // check if 1st layer is shown
        desktopCheckBoxPresent = driver.findElements(By.xpath("//*[@id=\"tree-node-desktop\"]")).size() > 0;
        assertThat(desktopCheckBoxPresent).isTrue();

        // expand all
        expandAllBtn.click();

        WebElement desktopCheckbox = driver.findElement(By.xpath("//*[@id=\"tree-node-desktop\"]"));
        WebElement desktopToggle = desktopCheckbox.findElement(By.xpath("./../../button"));

        // check if Desktop layer is shown
        Boolean notesCheckBoxPresent = driver.findElements(By.xpath("//*[@id=\"tree-node-notes\"]")).size() > 0;
        assertThat(notesCheckBoxPresent).isTrue();

        // collapse Desktop node
        desktopToggle.click();

        // check if Desktop layer is collapsed
        notesCheckBoxPresent = driver.findElements(By.xpath("//*[@id=\"tree-node-notes\"]")).size() > 0;
        assertThat(notesCheckBoxPresent).isFalse();

        WebElement veuCheckBox = driver.findElement(By.xpath("//*[@id=\"tree-node-veu\"]"));
        WebElement veuLabel = veuCheckBox.findElement(By.xpath("./.."));
        WebElement documentsCheckbox = driver.findElement(By.xpath("//*[@id=\"tree-node-documents\"]"));

        WebElement homeIcon = driver.findElement(By.cssSelector("#tree-node-home + span > svg"));
        WebElement documentsIcon = driver.findElement(By.cssSelector("#tree-node-documents + span > svg"));
        WebElement workSpaceIcon = driver.findElement(By.cssSelector("#tree-node-workspace + span > svg"));

        // check if Documents is unchecked
        assertThat(documentsCheckbox.isSelected()).isFalse();
        assertThat(documentsIcon.getAttribute("class")).containsIgnoringCase("rct-icon-uncheck");
        assertThat(workSpaceIcon.getAttribute("class")).containsIgnoringCase("rct-icon-uncheck");
        assertThat(homeIcon.getAttribute("class")).containsIgnoringCase("rct-icon-uncheck");

        // check 3rd layer element
        veuLabel.click();

        // find icons again
        homeIcon = driver.findElement(By.cssSelector("#tree-node-home + span > svg"));
        documentsIcon = driver.findElement(By.cssSelector("#tree-node-documents + span > svg"));
        workSpaceIcon = driver.findElement(By.cssSelector("#tree-node-workspace + span > svg"));

        // check if upper layers are checked
        assertThat(documentsIcon.getAttribute("class")).containsIgnoringCase("rct-icon-half-check");
        assertThat(workSpaceIcon.getAttribute("class")).containsIgnoringCase("rct-icon-half-check");
        assertThat(homeIcon.getAttribute("class")).containsIgnoringCase("rct-icon-half-check");

        WebElement resultOutput = driver.findElement(By.xpath("//*[@id=\"result\"]"));
        assertThat(resultOutput.getText())
                .containsIgnoringCase("veu")
                .doesNotContainIgnoringCase("react");

        WebElement downloadsCheckbox = driver.findElement(By.xpath("//*[@id=\"tree-node-downloads\"]"));
        WebElement wordFileCheckbox = driver.findElement(By.xpath("//*[@id=\"tree-node-wordFile\"]"));

        assertThat(downloadsCheckbox.isSelected()).isFalse();
        assertThat(wordFileCheckbox.isSelected()).isFalse();

        //select all elements
        homeLabel.click();

        assertThat(downloadsCheckbox.isSelected()).isTrue();
        assertThat(wordFileCheckbox.isSelected()).isTrue();
        assertThat(resultOutput.getText()).containsIgnoringCase("angular");

        homeLabel.click();
        assertThat(downloadsCheckbox.isSelected()).isFalse();
        assertThat(wordFileCheckbox.isSelected()).isFalse();

        //collapse all
        collapseAllBtn.click();
        desktopCheckBoxPresent = driver.findElements(By.xpath("//*[@id=\"tree-node-desktop\"]")).size() > 0;
        assertThat(desktopCheckBoxPresent).isFalse();
    }

    @AfterMethod
    public void tearDown() {
    driver.quit();
    }
}
