package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.ProjectManagementPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddEmptyTask extends BaseTest {


	@Test
	public void addEmptyTask() {
		ProjectManagementPage project = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToFirstProject()
			.newTask()
			.setNewTaskName("")
			.saveNewTask();
		// Verify that an error message "The title is required" is shown when saving a task with an empty title.
		assertEquals("The title is required", project.getErrorMessage());
	}


}
