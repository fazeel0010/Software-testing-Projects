package tests;

import org.junit.jupiter.api.Test;
import po.LoginPage;
import po.SwimlanesPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewSwimlane extends BaseTest {

	@Test
	public void addNewSwimlane() {
		SwimlanesPage swimlanes = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.swimlanes()
			.addSwimlane()
			.setName("New Swimlane 3")
			.save();
		// Verify that the last swimlane is the newly added "New Swimlane 3".
		assertEquals("New Swimlane 3", swimlanes.getLastSwimlaneName());
	}


}
