package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.PermissionsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AuthorizedUsers extends BaseTest {


	@Test
	public void showAuthorizedUsers() {
		PermissionsPage permissions = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.permissions();

		// Verify that the first allowed user on the permissions page is "admin".
		assertEquals("admin", permissions.getFirstAllowedUser());
	}


}
