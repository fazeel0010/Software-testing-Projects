package tests;


import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.SwimlanesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddEmptySwimlane extends BaseTest {

	@Test
	public void addNewSwimlane() {
		SwimlanesPage swimlanes = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.swimlanes()
			.addSwimlane()
			.save();

		// Verify that an error message "The name is required" is shown when adding a swimlane with an empty name.
		assertEquals("The name is required", swimlanes.getErrorMessage());
	}


}
