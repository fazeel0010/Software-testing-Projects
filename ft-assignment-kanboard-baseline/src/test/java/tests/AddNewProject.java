package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.ProjectSummaryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewProject extends BaseTest {


	@Test
	public void addNewProject() {
		ProjectSummaryPage project = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.newProject()
			.addNewProject("Test 2");

		// Verify that the new project's title is "Test 2".
		assertEquals("Test 2", project.getTitle());
		// Verify that the new project's status is "This project is open".
		assertEquals("This project is open", project.getStatus());

	}


}
