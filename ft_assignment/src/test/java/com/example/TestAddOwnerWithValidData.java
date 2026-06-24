package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TestAddOwnerWithValidData extends BaseTest {


    @Test
    public void TestAddOwnerWithValidDataM() throws InterruptedException {

        // System.setProperty("webdriver.chrome.driver",
        // "C:\\Users\\Busra\\Desktop\\chromedriver_win32\\chromedriver.exe");
        // driver = new ChromeDriver();
        // driver.get("http://localhost:8080/");

        String firstName = "Busra";
        String lastName = "Ertugrul";
        String address = "Via torti";
        String city = "Genova";
        String telephone = "3381564885";

        // Click find owner button to open find owner screen
        driver.findElement(By.cssSelector("[title='find owners']")).click();
        Thread.sleep(UI_WAIT_MS);

        // In find owner screen, click add owner button to add new owner
        driver.findElement(By.linkText("Add Owner")).click();
        Thread.sleep(UI_WAIT_MS);

        // Fill owner informations
        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("telephone")).sendKeys(telephone);

        // Click add owner button to save new owner
        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(UI_WAIT_MS);

        // Check if submit button is still on the page or not (Save operation is
        // successful or not)
        try {

            WebElement submitButton = driver.findElement(By.cssSelector("[type='submit']"));

            // Add Owner Button still here, so, save operation has an error.
            fail("Form Submit Error!");

        } catch (Exception e) { // Add owner button is not found, form is submitted successfully

            // Check if values are correctly saved or not
            assertEquals(firstName + " " + lastName,
                    driver.findElement(By.xpath("html[1]/body[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/b[1]"))
                            .getText(),
                    "FirstName and LastName could not saved correctly");
            assertEquals(address, driver
                    .findElement(By.xpath("html[1]/body[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[1]")).getText(),
                    "Address could not saved correctly");
            assertEquals(city, driver
                    .findElement(By.xpath("html[1]/body[1]/div[1]/div[1]/table[1]/tbody[1]/tr[3]/td[1]")).getText(),
                    "City could not saved correctly");
            assertEquals(telephone, driver
                    .findElement(By.xpath("html[1]/body[1]/div[1]/div[1]/table[1]/tbody[1]/tr[4]/td[1]")).getText(),
                    "Telephone could not saved correctly");
        }

    }

}
