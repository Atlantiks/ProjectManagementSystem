package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetDevelopersList implements Command {
    public static final String GET_DEVS_LIST = "get developers list";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull
    private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_LIST.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter project" + "\033[0;93m" + " id" + "\033[0m");
        Integer projectId = 0;

        try {
            projectId = Integer.parseInt(view.read());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        var devs = PROJECT_DAO.getListOfInvolvedDevelopers(projectId);

        if (devs.size() == 0) {
            System.out.printf("Разработчиков проекта с id = %d не найдено\n", projectId);
        } else {
            view.write(String.format("Список всех разработчиков проекта с id = %s\n", "\033[0;93m" + projectId + "\033[0m"));
            devs.stream()
                    .map(Developer::toString)
                    .forEach(view::write);
        }

        view.write("\nPlease, enter next command...");
    }
}
