package tests;

import org.junit.jupiter.api.Test;
import po.ApplicationSettingsPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChangeLanguage extends BaseTest {

	@Test
	public void changeLang() {
		ApplicationSettingsPage appSettings = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToSettings()
			.appSettings()
			.selectLanguage("Italiano")
			.save();

		// Verify that the selected language is "Italiano".
		assertEquals("Italiano", appSettings.getSelectedLanguage());
		// Verify that the language label reads "Lingua" (Italian for "Language").
		assertEquals("Lingua", appSettings.getLanguageLabel());
		appSettings.selectLanguage("English (US)").save();
		// Verify that the selected language is "English (US)" after switching back.
		assertEquals("English (US)", appSettings.getSelectedLanguage());
		// Verify that the language label reads "Language" again.
		assertEquals("Language", appSettings.getLanguageLabel());
	}


}
