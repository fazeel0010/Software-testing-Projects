package tests;

import org.junit.jupiter.api.Test;
import po.AddNewProjectPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddEmptyProject extends BaseTest {

	@Test
	public void addEmptyProject() {
		AddNewProjectPage newProjPage = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.newProject()
			.addEmptyProject();

		// Verify that an error message "The project name is required" appears when creating a project without a name.
		assertEquals("The project name is required", newProjPage.getErrorMessage());

	}
}
