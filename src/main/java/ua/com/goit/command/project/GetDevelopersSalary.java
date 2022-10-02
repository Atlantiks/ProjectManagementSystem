package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.view.View;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class GetDevelopersSalary implements Command {
    public static final String GET_DEVS_SALARY = "get salary of involved developers";
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_SALARY.equalsIgnoreCase(input);
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

        view.write(String.format("\nЗарплата всех разработчиков проекта с id = %s : %s\n", "\033[0;93m" + projectId + "\033[0m",
                PROJECT_DAO.getListOfInvolvedDevelopers(projectId)
                        .stream().map(Developer::getSalary)
                        .reduce(BigDecimal.valueOf(0.0), BigDecimal::add)));
    }
}
