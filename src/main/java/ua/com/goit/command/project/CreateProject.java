package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class CreateProject implements Command {
    public static final String CREATE_PROJECT = "create project";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_PROJECT.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Adding new project...soon");
    }
}
