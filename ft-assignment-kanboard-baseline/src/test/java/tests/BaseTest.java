package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

	// Change this to your local IP address or use "localhost" if running tests on the same machine as the application
	// public static final String app_url = "http://130.251.247.130:8080";
	public static final String app_url = "http://localhost:8080";
	public static final String password = "admin";
	public WebDriver driver;

	@BeforeEach
	public void setUp() {
		// Change this to setupNativeBrowser() if you want to run tests on your local machine
		// setupRemoteWebdriver();
		setupNativeBrowser();
		driver.get(app_url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
	}

	/**
	 * If you want to run tests on Selenium Grid in docker, use this method.
	 * Make sure to start the grid before running the tests.
	 * The {@code app_url} should be accessible from the grid container.
	 */
	public void setupRemoteWebdriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--no-sandbox", "--headless=new", "--disable-gpu", "--screen-info={1920x1080}");
		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
			driver.get(app_url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If you want to run tests on your local machine, use this method.
	 * Make sure to have the compatible version of ChromeDriver in your system PATH.
	 */
	public void setupNativeBrowser() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-search-engine-choice-screen", "--disable-gpu", "--headless=new", "--screen-info={1920x1080}");
		options.setBrowserVersion("127");
		driver = new ChromeDriver(options);
	}

	/**
	 * Saves the current page source to a file with the specified name.
	 */
	protected void saveWebPageSource(String filename) {
		try {
			var pageSource = driver.getPageSource();
			if (pageSource == null) {
				System.err.println("Page source is null, cannot save to file. " + filename);
				return;
			}
			Files.writeString(Paths.get("html/" + filename), pageSource);
		} catch (java.io.IOException e) {
			System.err.println("Error saving page source: " + e.getMessage());
		}
	}

	@AfterEach
	public void quit() {
		driver.quit();
	}

}
