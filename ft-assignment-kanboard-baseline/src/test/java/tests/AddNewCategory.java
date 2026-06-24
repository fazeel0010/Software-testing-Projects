package tests;

import org.junit.jupiter.api.Test;
import po.CategoriesPage;
import po.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddNewCategory extends BaseTest {

	@Test
	public void addNewCategory() {
		CategoriesPage categories = new LoginPage(driver)
			.loginToKanboard("admin", password)
			.firstProjectSummary()
			.categories()
			.addCategory()
			.setName("New Category 2")
			.save();

		// Verify that the categories list contains the newly added category "New Category 2".
		assertEquals("New Category 2", categories.getCategoryName());
	}


}
