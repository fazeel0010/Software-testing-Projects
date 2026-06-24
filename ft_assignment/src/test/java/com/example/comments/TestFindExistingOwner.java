package com.example.comments;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

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
		assertTrue(driver.findElement(By.cssSelector("table tbody tr")).getText().contains(lastName));
    }

}
