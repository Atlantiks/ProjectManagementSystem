package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.entity.Project;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.time.LocalDate;
import java.util.Objects;

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
        view.write("To create new Project you have to enter mandatory project name " +
                "and optionally description and status.");
        view.write("1. Please enter new Project's name:");
        String projectName = view.read();
        if (projectName.isBlank()) throw new BlancFieldException();

        Project newProject = new Project(projectName);
        newProject.setDate_created(LocalDate.now());

        view.write("Would you like to add additional project info? Y/N  Press ENTER for default option(N)");

        String userAnswer = view.read();
        userAnswer = userAnswer.isBlank() ? "N" : userAnswer.substring(0, 1).toUpperCase();

        switch (userAnswer) {
            case "Y":
                view.write("2. Please enter new Project's description:");
                String desc = view.read();
                view.write("3. Please enter new Project's status:");
                view.write("(Choose from: Active, Inactive , Discontinued , Not commissioned)");
                String status = view.read();

                newProject.setDescription(desc);
                newProject.setStatus(status);

                break;
            case "N":
                break;
            default:
                view.write("Answer not recognised. Default parameters will be applied");
                break;
        }

        Project savedProject = PROJECT_DAO.save(newProject, view);

        if (Objects.nonNull(savedProject.getId())) {
            view.write("\033[0;92mThe following project was successfully added to database:\033[0m");
            view.write(savedProject + "\n");
        }
    }
}
