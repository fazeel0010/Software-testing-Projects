package com.example.commentsAndPages;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestAddOwnerWithEmptyFirstName extends BaseTest {


	@Test
	public void TestAddOwnerWithEmptyFirstNameM() throws InterruptedException {

		String firstName = "";
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

		// The first name validation message should say "must not be blank" after submitting an empty first name.
		assertEquals("must not be blank", driver.findElement(By.className("help-inline")).getText());
	}

}
