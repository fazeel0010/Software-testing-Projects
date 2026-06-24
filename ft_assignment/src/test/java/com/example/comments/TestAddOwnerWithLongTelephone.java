package com.example.comments;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestAddOwnerWithLongTelephone extends BaseTest {


    @Test
    public void TestAddOwnerWithLongTelephoneM() throws InterruptedException {

        String firstName = "Busra";
        String lastName = "Ertugrul";
        String address = "Via Torti";
        String city = "Genova";
        String telephone = "1234567898765";

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

        // Check if there is an error message or not
        try {

			// The telephone validation message should include "Telephone must be a 10-digit number" for an overlong value.
            assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Telephone must be a 10-digit number')]")).getText().contains("Telephone must be a 10-digit number"));

        } catch (Exception e) { // Error Message is not found

            fail("Numeric value out of bounds (<10 digits>.<0 digits> expected) error message could not found(For Telephone): "
                    + e.getMessage());
        }

    }

}
