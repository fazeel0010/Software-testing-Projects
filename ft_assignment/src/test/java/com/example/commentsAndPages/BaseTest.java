package com.example.commentsAndPages;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Files;

public class BaseTest {
	protected static final long UI_WAIT_MS = 500L;
	protected static WebDriver driver;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/");
	}

	@AfterEach
	void tearDown() {
		driver.quit();
		driver = null;
	}

	/**
	 * Saves the current page source to a file with the specified name.
	 */
	protected void saveWebPageSource(String filename) {
		try {
			var pageSource = driver.getPageSource();
			Files.write(java.nio.file.Paths.get(filename), pageSource.getBytes());
		} catch (java.io.IOException e) {
			System.err.println("Error saving page source: " + e.getMessage());
		}
	}
}
