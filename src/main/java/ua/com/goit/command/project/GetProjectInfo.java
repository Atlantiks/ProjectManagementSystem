package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetProjectInfo implements Command {
    public static final String GET_INFO = "get projects info";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return GET_INFO.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write(PROJECT_DAO.getProjectInfo());
    }
}
