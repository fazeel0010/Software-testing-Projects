package com.example.commentsAndPages;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestEditOwnerWithNotNumericTelephone extends BaseTest {


	@Test
	public void TestEditOwnerWithNotNumericTelephoneM() throws InterruptedException {

		String telephone = "abcdef";
		String lastName = "Black";

		driver.findElement(By.cssSelector("[title='find owners']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.id("lastName")).sendKeys(lastName);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.linkText("Edit Owner")).click();
		Thread.sleep(UI_WAIT_MS);

		driver.findElement(By.id("telephone")).clear();
		driver.findElement(By.id("telephone")).sendKeys(telephone);

		driver.findElement(By.cssSelector("[type='submit']")).click();
		Thread.sleep(UI_WAIT_MS);

		try {

			// The telephone validation message should say "Telephone must be a 10-digit number" for a non-numeric edited value.
			assertEquals("Telephone must be a 10-digit number", driver.findElement(By.className("help-inline")).getText());
		} catch (Exception e) {

			fail("Telephone control does not work !: " + e.getMessage());
		}
	}

}
