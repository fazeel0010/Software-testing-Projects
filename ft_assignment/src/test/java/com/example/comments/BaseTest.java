package com.example.comments;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected static final long UI_WAIT_MS = 500L;
    protected static WebDriver driver;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
