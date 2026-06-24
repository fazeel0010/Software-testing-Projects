package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.ProjectListPage;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class RemoveProject extends BaseTest {


	@Test
	public void removeProject() {
		ProjectListPage projects = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.remove();

		// Verify that the project "Test 2" no longer appears in the projects list after removal.
		assertFalse(projects.containsProject("Test 2"));
	}

}
