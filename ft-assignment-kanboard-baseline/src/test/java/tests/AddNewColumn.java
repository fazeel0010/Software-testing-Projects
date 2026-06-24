package tests;

import org.junit.jupiter.api.Test;
import po.ColumnsPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewColumn extends BaseTest {


	@Test
	public void addNewColumn() {
		ColumnsPage columns = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.columns()
			.addColumn()
			.setColumnName("New Column 3")
			.save();

		// Verify that the last column is the newly added "New Column 3".
		assertEquals("New Column 3", columns.getLastColumnName());
	}


}
