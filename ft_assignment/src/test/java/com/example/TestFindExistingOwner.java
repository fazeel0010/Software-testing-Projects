package com.example;

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
        // Check if the first item of the list has wanted lastname
        assertTrue(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/b[1]"))
                .getText().contains(lastName));
    }

}
