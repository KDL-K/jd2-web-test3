package by.htp.ts.command;

import java.util.HashMap;
import java.util.Map;
import by.htp.ts.command.impl.*;

public final class CommandProvider {
	private Map<CommandName, Command> commands=new HashMap<CommandName,Command>();
	
	public CommandProvider() {
		commands.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.LOGOUT, new LogOutCommand());
		
		commands.put(CommandName.SAVE_TEST_NAME_AND_DURATION, new SaveTestNameAndDuration());
		commands.put(CommandName.GO_TO_TESTCREATION_PAGE, new GoToTestCreationPage());
		commands.put(CommandName.GO_TO_MARK_RIGHT_ANSWERS_PAGE, new GoToMarkRightAnswersPage());
		commands.put(CommandName.GO_TO_QUESTION_ADD_PAGE, new GoToQuestionAddPage());
		commands.put(CommandName.SAVE_TEST, new SaveTestCommand());
		commands.put(CommandName.QUESTION_SAVE, new QuestionSaveCommand());
		commands.put(CommandName.CREATED_TESTS, new CreatedTestsCommand());
		commands.put(CommandName.GO_TO_ADD_Q_SAVE_T_PAGE, new GoToAddQSaveTPage());
		commands.put(CommandName.GO_TO_CREATED_TESTS_PAGE, new GoToCreatedTestsPage());
		commands.put(CommandName.GO_TO_TEST_LIST_PAGE, new GoToTestListPage());
		commands.put(CommandName.EDIT_TEST, new EditTestCommand());
		commands.put(CommandName.GO_TO_TEST_EDITION_PAGE, new GoToTestEditionPage());
		commands.put(CommandName.TEST_EDITION, new TestEditionCommand());
		commands.put(CommandName.TEST_LIST, new TestListCommand());
		commands.put(CommandName.START_TEST, new StartTestCommand());
		commands.put(CommandName.GO_TO_START_TEST_PAGE, new GoToStartTestPage());
		commands.put(CommandName.FINISH_TEST, new FinishTestCommand());
		commands.put(CommandName.GO_TO_TEST_RESULT_PAGE, new GoToTestResultPage());
		commands.put(CommandName.PROGRESS, new ProgressCommand());
		commands.put(CommandName.DELETE_TEST, new DeleteTestCommand());
		commands.put(CommandName.GO_TO_SOME_PAGE, new GoToSomePage());
		commands.put(CommandName.GO_TO_TEST_CREATION_PAGE, new GoToTestCreationPage());
		
		commands.put(CommandName.GO_TO_AUTHORIZATION_PAGE, new GoToAuthorizationPage());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
		
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
		commands.put(CommandName.GO_TO_ERROR_PAGE, new GoToErrorPage());
	}
	
	public Command getCommand(String commandStr) {
		CommandName commandName=CommandName.valueOf(commandStr.toUpperCase());
		return commands.get(commandName);
	}

}
