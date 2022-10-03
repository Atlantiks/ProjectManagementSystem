package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class FindProjectById implements Command {
    public static final String FIND_PROJECT_BY_ID = "find project by id";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return FIND_PROJECT_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter Project's id:");
        Integer projectId = Integer.parseInt(view.read());

        PROJECT_DAO.findById(projectId).ifPresentOrElse(
                dev -> view.write(dev.toString()),
                () -> view.write(
                        String.format("\033[0;91mProject with Id = %d wasn't found\033[0m", projectId)));
    }
}
