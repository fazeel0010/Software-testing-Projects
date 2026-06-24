package com.example.commentsAndPages;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddOwnerWithEmptyTelephone extends BaseTest {


	@Test
	public void TestAddOwnerWithEmptyTelephoneM() throws InterruptedException {

		String firstName = "Busra";
		String lastName = "Ertugrul";
		String address = "Via Torti";
		String city = "Genova";
		String telephone = "";

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

		// The telephone validation message should include "must not be blank" after submitting an empty telephone.
		assertTrue(driver.findElement(By.className("help-inline")).getText().contains("must not be blank"));

	}

}
