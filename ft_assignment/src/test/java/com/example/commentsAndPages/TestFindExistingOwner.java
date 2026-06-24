package com.example.commentsAndPages;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFindExistingOwner extends BaseTest {


	@Test
	public void TestFindExistingOwnerM() throws InterruptedException {
		String lastName = "Black";
		driver.findElement(By.cssSelector("[title='find owners']")).click();
		Thread.sleep(UI_WAIT_MS);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		// The first owner result should contain the searched last name.
		assertTrue(driver.findElement(By.cssSelector("td b")).getText().contains(lastName));
	}

}
