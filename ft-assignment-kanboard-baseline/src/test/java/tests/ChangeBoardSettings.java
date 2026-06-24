package tests;

import org.junit.jupiter.api.Test;
import po.BoardSettingsPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChangeBoardSettings extends BaseTest {


	@Test
	public void changeBoardSettings() {
		BoardSettingsPage boardSettings = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToSettings()
			.boardSettings()
			.setPublicrefreshIntgerval("85")
			.save()
			.appSettings()
			.boardSettings();

		// Verify that the public board refresh interval was saved as "85".
		assertEquals("85", boardSettings.getPublicRefreshInterval());
	}


}
