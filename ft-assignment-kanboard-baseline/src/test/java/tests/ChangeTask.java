package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.TaskPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeTask extends BaseTest {

	@Test
	public void changeTask() {
		TaskPage task = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.goToProject("Test 2")
			.goToTask("task 3")
			.closeTask()
			.confirmCloseTask();

		// Verify that the task status is "closed" after closing it.
		assertEquals("closed", task.getStatus());
	}


}
