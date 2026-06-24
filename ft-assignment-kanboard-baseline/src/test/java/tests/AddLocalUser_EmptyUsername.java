package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.NewUserPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddLocalUser_EmptyUsername extends BaseTest {

	@Test
	public void addEmptyUser() {
		NewUserPage user = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.usersManagement()
			.newUser()
			.setFullname("User2")
			.setEmail("user2@gmail.com")
			.setPassword("test123")
			.confirmPassword("test123")
			.saveError();

		// Verify that a username error "The username is required" is shown when the username field is empty.
		assertEquals("The username is required", user.getNameError());
	}

}
