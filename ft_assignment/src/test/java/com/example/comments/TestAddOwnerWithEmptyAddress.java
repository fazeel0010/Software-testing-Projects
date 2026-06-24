package com.example.comments;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestAddOwnerWithEmptyAddress extends BaseTest {


    @Test
    public void TestAddOwnerWithEmptyAddressM() throws InterruptedException {

        String firstName = "Busra";
        String lastName = "Ertugrul";
        String address = "";
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

		// The address validation message should say "must not be blank" after submitting an empty address.
		assertEquals("must not be blank", driver.findElement(By.className("help-inline")).getText());

    }

}
