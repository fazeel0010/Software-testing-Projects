package com.example.nocomments;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestFindNotExistingOwner extends BaseTest {


	@Test
	public void TestFindNotExistingOwnerM() throws InterruptedException {
		String lastName = "Rossi";
		driver.findElement(By.cssSelector("[title='find owners']")).click();
		Thread.sleep(UI_WAIT_MS);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		org.junit.jupiter.api.Assertions.assertTrue(driver.getPageSource().contains("No owners found"));
	}

}
