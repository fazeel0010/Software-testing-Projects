package com.example.nocomments;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.fail;

public class TestEditOwnerWithValidData extends BaseTest {


	@Test
	public void TestEditOwnerWithValidDataM() throws InterruptedException {

		String firstName = "Bobo";
		String lastName = "Black";

		driver.findElement(By.cssSelector("[title='find owners']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.id("lastName")).sendKeys(lastName);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.linkText("Edit Owner")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.id("firstName")).clear();
		driver.findElement(By.id("firstName")).sendKeys(firstName);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		try {

			org.junit.jupiter.api.Assertions.assertTrue(driver.getPageSource().contains(firstName));

		} catch (Exception e) {

			fail("Edit Owner Error: " + e.getMessage());
		}

	}

}
