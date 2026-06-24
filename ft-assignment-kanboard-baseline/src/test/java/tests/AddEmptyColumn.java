package tests;

import org.junit.jupiter.api.Test;
import po.ColumnsPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddEmptyColumn extends BaseTest {

	@Test
	public void addEmptyColumn() {
		ColumnsPage columns = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.columns()
			.addColumn()
			.save();

		// Verify that an error message "The title is required" is shown when submitting a column with no title.
		assertEquals("The title is required", columns.getErrorMessage());
	}
}
