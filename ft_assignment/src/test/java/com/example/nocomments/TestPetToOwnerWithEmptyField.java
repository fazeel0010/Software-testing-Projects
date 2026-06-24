package com.example.nocomments;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.fail;

public class TestPetToOwnerWithEmptyField extends BaseTest {


	@Test
	public void TestPetToOwnerWithEmptyFieldM() throws InterruptedException {

		String lastName = "Black";

		driver.findElement(By.cssSelector("[title='find owners']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.id("lastName")).sendKeys(lastName);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.linkText("Add New Pet")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		try {

			Thread.sleep(UI_WAIT_MS);

			org.junit.jupiter.api.Assertions.assertTrue(driver.findElement(By.cssSelector("[type='submit']")).isDisplayed());

		} catch (Exception e) {

			fail("New Pet Add empty field control could not found!: " + e.getMessage());

		}
	}

}
