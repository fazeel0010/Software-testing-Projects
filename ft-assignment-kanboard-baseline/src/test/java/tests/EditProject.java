package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.ProjectSummaryPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EditProject extends BaseTest {


	@Test
	public void editProduct() {
		ProjectSummaryPage project = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.editProject()
			.setDescription("This is the new description")
			.save()
			.summary();

		// Verify that the project description was updated to "This is the new description".
		assertEquals("This is the new description", project.getDescription());

	}


}
