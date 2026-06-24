package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.SearchResultsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchInProject extends BaseTest {

	@Test
	public void searchInProject() {
		SearchResultsPage results = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.search("status:open");

		// Verify that the first result of the open-tasks search is "task 3".
		assertEquals("task 3", results.getFirstTask());
	}


}
