package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.UserSummaryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddRemoteUser extends BaseTest {


	@Test
	public void addRemoteUser() {
		UserSummaryPage user = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.usersManagement()
			.newUser()
			.setUsername("TestRemote")
			.setFullname("remote1")
			.setEmail("remote@gmail.com")
			.setRemote()
			.save();

		// Verify that the created remote user's username is "TestRemote".
		assertEquals("TestRemote", user.getUsername());
		// Verify that the created remote user's full name is "remote1".
		assertEquals("remote1", user.getFullname());
		// Verify that the created remote user's email is "remote@gmail.com".
		assertEquals("remote@gmail.com", user.getEmail());
		// Verify that the created user's account type is "Remote".
		assertEquals("Remote", user.getAccountType());
	}


}
