package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestFindAllOwner extends BaseTest {


    @Test
    public void TestFindAllOwnerM() throws InterruptedException {

        driver.findElement(By.cssSelector("[title='find owners']")).click();
        Thread.sleep(UI_WAIT_MS);

        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(UI_WAIT_MS);

        // Check if there is a table for owners or not
        driver.findElement(By.id("owners"));
        assertTrue(true);

    }

}
