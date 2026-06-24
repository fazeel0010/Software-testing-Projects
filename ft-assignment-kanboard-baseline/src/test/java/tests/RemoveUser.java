package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.UsersManagementPage;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class RemoveUser extends BaseTest {


	@Test
	public void removeUser() {
		UsersManagementPage users = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.usersManagement()
			.removeFirstUser();

		// Verify that the user "remote1" no longer appears in the users list after removal.
		assertFalse(users.containsUser("remote1"));
	}


}
