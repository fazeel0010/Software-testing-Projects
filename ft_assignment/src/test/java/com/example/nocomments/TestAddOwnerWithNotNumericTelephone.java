package com.example.nocomments;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.fail;

public class TestAddOwnerWithNotNumericTelephone extends BaseTest {


	@Test
	public void TestAddOwnerWithNotNumericTelephoneM() throws InterruptedException {

		String firstName = "Busra";
		String lastName = "Ertugrul";
		String address = "Via Torti";
		String city = "Genova";
		String telephone = "abcdef";

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

		try {

			org.junit.jupiter.api.Assertions.assertTrue(driver.findElement(By.cssSelector("[type='submit']")).isDisplayed());

		} catch (Exception e) {

			fail("Numeric value out of bounds (<10 digits>.<0 digits> expected) error message could not found(For Telephone): "
				+ e.getMessage());
		}

	}

}
