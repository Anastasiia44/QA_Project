package com.example.qaproject;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WebTablesTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        File file = new File("src/test/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();

        driver.navigate().to("https://demoqa.com/elements");
        WebElement menuItem3 = driver.findElement(By.id("item-3"));
        menuItem3.click();

        String URL = driver.getCurrentUrl();
        assertThat(URL).isEqualToIgnoringCase("https://demoqa.com/webtables");

    }

    @Test
    @SneakyThrows
    public void registrationFormTest() {

        //checking that the 4th row is empty
        WebElement emptyRow = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]"));
        assertThat(emptyRow).doesNotHaveToString("First Name");

        WebElement addNewRecordButton = driver.findElement(By.id("addNewRecordButton"));
        addNewRecordButton.click();

        //finding elements
        WebElement firstNameInput = driver.findElement(By.xpath("//*[@id=\"firstName\"]"));
        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement userEmail = driver.findElement(By.xpath("//*[@id=\"userEmail\"]"));
        WebElement age = driver.findElement(By.xpath("//*[@id=\"age\"]"));
        WebElement salary = driver.findElement(By.xpath("//*[@id=\"salary\"]"));
        WebElement department = driver.findElement(By.xpath("//*[@id=\"department\"]"));

        WebElement submit = driver.findElement(By.id("submit"));


        //checking First Name, Second Name and Department
        firstNameInput.clear();
        firstNameInput.sendKeys("First Name");

        lastName.clear();
        lastName.sendKeys("Last Name");

        department.clear();
        department.sendKeys("IT");

        assertThat(firstNameInput.getAttribute("value")).isEqualToIgnoringCase("First Name")
                .isNotEmpty()
                .isNotEqualToIgnoringCase("Second Name");

        assertThat(lastName.getAttribute("value")).isEqualToIgnoringCase("Last Name")
                .isNotEmpty()
                .isNotEqualToIgnoringCase("Phone number");

        assertThat(department.getAttribute("value")).isEqualToIgnoringCase("IT")
                .isNotEmpty()
                .isNotEqualToIgnoringCase("First Name");

        submit.click();

        //checking email
        String invalidEmail = "wrong.email";
        String validEmail = "valid.email@test.ua";

        // wrong email
        userEmail.clear();
        userEmail.sendKeys(invalidEmail);
        submit.click();

        Boolean emailIsInvalid = driver.findElements(By.cssSelector("#userEmail:invalid")).size() > 0;
        assertThat(emailIsInvalid).isTrue();

        // valid email
        userEmail.clear();
        userEmail.sendKeys(validEmail);
        submit.click();

        emailIsInvalid = driver.findElements(By.cssSelector("#userEmail:invalid")).size() > 0;
        assertThat(emailIsInvalid).isFalse();


        assertThat(userEmail.getAttribute("value")).isNotEmpty()
                .containsIgnoringCase(validEmail)
                .doesNotContainIgnoringCase(invalidEmail);

        //checking age
        String invalidAge = "a15";
        String validAge = "27";

        //invalid age
        age.clear();
        age.sendKeys(invalidAge);
        submit.click();

        Boolean ageIsInvalid = driver.findElements(By.cssSelector("#age:invalid")).size() > 0;
        assertThat(ageIsInvalid).isTrue();

        // valid age
        age.clear();
        age.sendKeys(validAge);
        submit.click();

        ageIsInvalid = driver.findElements(By.cssSelector("#age:invalid")).size() > 0;
        assertThat(ageIsInvalid).isFalse();

        assertThat(age.getAttribute("value")).isNotEmpty()
                .containsIgnoringCase(validAge)
                .doesNotContainIgnoringCase(invalidAge);

        //checking salary
        String invalidSalary = "money";
        String validSalary = "1000";

        //invalid salary
        salary.clear();
        salary.sendKeys(invalidSalary);
        submit.click();

        Boolean salaryIsInvalid = driver.findElements(By.cssSelector("#salary:invalid")).size() > 0;
        assertThat(salaryIsInvalid).isTrue();

        // valid salary
        salary.clear();
        salary.sendKeys(validSalary);


        salaryIsInvalid = driver.findElements(By.cssSelector("#salary:invalid")).size() > 0;
        assertThat(salaryIsInvalid).isFalse();

        assertThat(salary.getAttribute("value")).isNotEmpty()
                .containsIgnoringCase(validSalary)
                .doesNotContainIgnoringCase(invalidSalary);

        submit.click();

        //checking if new info was added
        WebElement addedName = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[1]"));
        WebElement addedEmail = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[4]"));

        assertThat(addedName.getText()).isNotEmpty()
                .containsIgnoringCase("first name");
        assertThat(addedEmail.getText()).containsIgnoringCase("valid.email@test.ua")
                .isNotEqualToIgnoringCase("wrong.email")
                .isNotEmpty();

        //editing record

        WebElement editRecord4 = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[7]/div/span[1]"));
        editRecord4.click();


        salary = driver.findElement(By.xpath("//*[@id=\"salary\"]"));
        salary.clear();
        salary.sendKeys("9000");

        assertThat(salary.getAttribute("value")).isNotEmpty()
                .isEqualToIgnoringCase("9000");

        submit = driver.findElement(By.id("submit"));
        submit.click();

        WebElement editedNewElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[4]/div/div[5]"));
        assertThat(editedNewElement.getText()).isNotEmpty()
                .isEqualToIgnoringCase("9000")
                .isNotEqualToIgnoringCase("1000");

        //deleting a string

        WebElement deleteRecord4 = driver.findElement(By.xpath("//*[@id=\"delete-record-4\"]"));
        deleteRecord4.click();

        assertThat(emptyRow).doesNotHaveToString("first name");
    }

    @Test
    public void testWebTables() {
        //Finding number of Rows
        List<WebElement> rowsNumber = driver.findElements(By.className("rt-tr-group"));
        int rowCount = rowsNumber.size();
        assertThat(rowCount).isEqualTo(10);

        //selecting different value of rows from the dropdown list

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div[2]/span[2]/select")));
        select.selectByValue("5");

        rowsNumber = driver.findElements(By.className("rt-tr-group"));
        rowCount = rowsNumber.size();
        assertThat(rowCount).isEqualTo(5)
                .isNotEqualTo(10);

        //checking the possibility to jump the page
        WebElement jumpPageInput = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div[2]/span[1]/div/input"));

        jumpPageInput.clear();
        jumpPageInput.sendKeys("20");

        assertThat(jumpPageInput.getAttribute("value")).isEqualToIgnoringCase("1")
                .isNotEqualToIgnoringCase("20")
                .isNotEmpty();

        //checking that the Previous and  the Next buttons are disabled
        WebElement previousBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div[1]/button"));
        WebElement nextBtn = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[2]/div/div[3]/button"));

        assertThat(previousBtn.isEnabled()).isFalse();
        assertThat(nextBtn.isEnabled()).isFalse();
    }

    @Test
    public void filterTest(){
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchBox\"]"));
        searchBox.clear();

        //sending invalid value
        searchBox.sendKeys("aaa");

        WebElement firstCell = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[1]/div/div[1]"));
        assertThat(firstCell).doesNotHaveToString("aaa")
                .hasNoNullFieldsOrProperties();

        //sending valid value
        searchBox.clear();
        searchBox.sendKeys("Cierra");
        firstCell = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[1]/div/div[1]"));
        assertThat(firstCell.getText()).containsIgnoringCase("Cierra")
                .isNotEmpty();

        searchBox.clear();
        searchBox.sendKeys("10000");
        WebElement firstSalaryCell = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/div[1]/div[2]/div[1]/div/div[5]"));
        assertThat(firstSalaryCell.getText()).containsIgnoringCase("10000")
                .isNotEmpty()
                .doesNotContainIgnoringCase("2000");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
