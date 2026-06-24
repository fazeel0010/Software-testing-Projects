package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	AddNewProject.class,
	AddEmptyProject.class,
	AddNewTask.class,
	AddEmptyTask.class,
	EditProject.class,
	AddNewColumn.class,
	AddEmptyColumn.class,
	AddNewSwimlane.class,
	AddEmptySwimlane.class,
	AddNewCategory.class,
	AddEmptyCategory.class,
	EditProfile.class,
	AuthorizedUsers.class,
	SetReferenceCurrency.class,
	ChangeLanguage.class,
	AddNewPrivateProject.class,
	SearchInProject.class,
	ChangeTask.class,
	AddLocalUser.class,
	AddRemoteUser.class,
	AddLocalUser_DifferentPasswords.class,
	AddLocalUser_EmptyConfirmPassword.class,
	AddLocalUser_EmptyPassword.class,
	AddLocalUser_EmptyUsername.class,
	AddLocalUser_ShortPassword.class,
	RemoveUser.class,
	ClosedTasks.class,
	RemoveProject.class,
	ChangeBoardSettings.class,
	AddNewCurrencyRate.class,
})

public class TestSuite {
}
