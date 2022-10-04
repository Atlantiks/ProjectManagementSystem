package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeleteProjectById implements Command {
    public static final String DEL_PROJECT_BY_ID = "delete project by id";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return DEL_PROJECT_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter Project's id:");
        Integer projectId = Integer.parseInt(view.read());

        if (PROJECT_DAO.removeById(projectId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete Project with following Id = %d", projectId ));
        }
    }
}
