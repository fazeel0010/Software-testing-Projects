package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosedTasks extends BaseTest {


	@Test
	public void searchClosedTask() {
		SearchResultsPage results = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.search("status:closed");

		// Verify that the first result of the closed-tasks search is "task 3".
		assertEquals("task 3", results.getFirstTask());
	}


}
