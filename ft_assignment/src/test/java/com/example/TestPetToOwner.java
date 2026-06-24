package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class TestPetToOwner extends BaseTest {


    @Test
    public void TestPetToOwnerM() throws InterruptedException {

        String petName = "Bird10";
        String lastName = "Black";

        driver.findElement(By.cssSelector("[title='find owners']")).click();
        Thread.sleep(UI_WAIT_MS);

        driver.findElement(By.id("lastName")).sendKeys(lastName);

        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(UI_WAIT_MS);

        driver.findElement(By.linkText("Add New Pet")).click();
        Thread.sleep(UI_WAIT_MS);

        driver.findElement(By.id("name")).sendKeys(petName);
        driver.findElement(By.id("birthDate")).sendKeys("2015-01-10");
        driver.findElement(By.id("type")).sendKeys("bird");

        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(UI_WAIT_MS);

        try {

            driver.findElement(By.xpath("//button[text()='Add Pet']"));

            fail("You can not add  new pet with existed pet name ! ");

        } catch (Exception e) {

            try {

                assertEquals(petName,
                        driver.findElement(By.xpath("//dd[contains(text()," + "'" + petName + "')]")).getText(),
                        "Wrong petname added!");

            } catch (Exception ex) {

                fail("New Pet could not added !: " + e.getMessage());

            }
        }
    }

}
