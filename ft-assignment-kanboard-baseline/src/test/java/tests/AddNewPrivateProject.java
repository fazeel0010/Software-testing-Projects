package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.ProjectSummaryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewPrivateProject extends BaseTest {


	@Test
	public void addNewPrivateProject() {
		ProjectSummaryPage project = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.newPersonalProject()
			.addNewProject("Test private 2");

		// Verify that the new project's title is "Test private 2".
		assertEquals("Test private 2", project.getTitle());
		// Verify that the new project's status is "This project is open".
		assertEquals("This project is open", project.getStatus());
		// Verify that the project's access level indicates "This project is personal".
		assertEquals("This project is personal", project.getAccessLevel());
		// Verify that public access is "Public access disabled".
		assertEquals("Public access disabled", project.getPublicAccess());
	}


}
