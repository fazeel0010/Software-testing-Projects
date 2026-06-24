package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.UserSummaryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddLocalUser extends BaseTest {


	@Test
	public void addLocalUser() {
		UserSummaryPage user = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.usersManagement()
			.newUser()
			.setUsername("TestUser")
			.setFullname("User1")
			.setEmail("user@gmail.com")
			.setPassword("test123")
			.confirmPassword("test123")
			.save();

		// Verify that the created user's username is "TestUser".
		assertEquals("TestUser", user.getUsername());
		// Verify that the created user's full name is "User1".
		assertEquals("User1", user.getFullname());
		// Verify that the created user's email is "user@gmail.com".
		assertEquals("user@gmail.com", user.getEmail());

	}


}
