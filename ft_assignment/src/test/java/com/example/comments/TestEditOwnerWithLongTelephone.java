package com.example.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestEditOwnerWithLongTelephone extends BaseTest {


    

	@Test
	public void TestEditOwnerWithLongTelephoneM() throws InterruptedException {

		String telephone = "1234567898765";
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

			// The telephone validation message should say "Telephone must be a 10-digit number" for an overlong edited value.
			assertEquals("Telephone must be a 10-digit number", driver.findElement(By.xpath("//*[contains(text(), 'Telephone must be a 10-digit number')]")).getText());

		} catch (Exception e) {

			fail("Telephone control does not work !: " + e.getMessage());
		}
	}


}
