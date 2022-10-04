package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Project;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ProjectService {
    private static final ProjectService PROJECT_SERVICE = new ProjectService();
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    @Getter
    @Setter
    private View view;

    private ProjectService() {
    }

    public static ProjectService getInstance() {
        return PROJECT_SERVICE;
    }


    public void createProject() {
        view.write("To create new Project you have to enter :\n--mandatory project name " +
                "\n--optionally description and status.");
        view.write("1. Please enter new Project's name:");
        String projectName = view.read();
        if (projectName.isBlank()) {
            throw new BlancFieldException();
        } else {
            projectName = Formatter.capitalize(projectName);
        }

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
                String status = Formatter.capitalize(view.read());

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

    public void deleteProjectById() {
        view.write("Please enter Project's id:");
        Integer projectId = Integer.parseInt(view.read());

        if (PROJECT_DAO.removeById(projectId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete Project with following Id = %d", projectId ));
        }
    }

    public void findProjectById() {
        view.write("Please enter Project's id:");
        Integer projectId = Integer.parseInt(view.read());

        PROJECT_DAO.findById(projectId).ifPresentOrElse(
                dev -> view.write(dev.toString()),
                () -> view.write(
                        String.format("\033[0;91mProject with Id = %d wasn't found\033[0m", projectId)));
    }

    public void getDevelopersList() {
        view.write("Please enter project" + "\033[0;93m" + " id" + "\033[0m");
        Integer projectId = Integer.parseInt(view.read());

        var devs = PROJECT_DAO.getListOfInvolvedDevelopers(projectId);

        if (devs.size() == 0) {
            System.out.printf("Разработчиков проекта с id = %d не найдено\n", projectId);
        } else {
            view.write(String.format("Список всех разработчиков проекта с id = %s\n", "\033[0;93m" + projectId + "\033[0m"));
            devs.stream()
                    .map(Developer::toString)
                    .forEach(view::write);
        }
    }

    public void getDevelopersSalary() {
        view.write("Please enter project" + "\033[0;93m" + " id" + "\033[0m");
        Integer projectId = Integer.parseInt(view.read());

        view.write(String.format("\nЗарплата всех разработчиков проекта с id = %s : %s\n", "\033[0;93m" + projectId + "\033[0m",
                PROJECT_DAO.getListOfInvolvedDevelopers(projectId)
                        .stream().map(Developer::getSalary)
                        .reduce(BigDecimal.valueOf(0.0), BigDecimal::add)));
    }

    public void getProjectInfo() {
        view.write(PROJECT_DAO.getProjectInfo());
    }
}
