package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.NewUserPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddLocalUser_DifferentPasswords extends BaseTest {

	@Test
	public void addDifferentPasswords() {
		NewUserPage user = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.usersManagement()
			.newUser()
			.setUsername("TestUser2")
			.setFullname("User2")
			.setEmail("user2@gmail.com")
			.setPassword("test123")
			.confirmPassword("123test")
			.saveError();

		// Verify that a password error "Passwords don't match" is shown when the password and confirmation differ.
		assertEquals("Passwords don't match", user.getPswConfirmError());
	}

}
