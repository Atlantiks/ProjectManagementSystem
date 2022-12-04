package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.dto.*;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Project;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.mapper.CreateDeveloperMapper;
import ua.com.goit.mapper.CreateProjectMapper;
import ua.com.goit.mapper.UpdateProjectMapper;
import ua.com.goit.validation.CreateProjectValidator;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProjectService {
    private static final ProjectService PROJECT_SERVICE = new ProjectService();
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    private static final ProjectDao PROJECT_DAO = ProjectDao.getInstance();
    private static final CreateProjectValidator PROJECT_VALIDATOR = CreateProjectValidator.getInstance();
    private static final CreateProjectMapper PROJECT_MAPPER = CreateProjectMapper.getInstance();
    private static final CreateDeveloperMapper DEV_MAPPER = CreateDeveloperMapper.getInstance();
    private static final UpdateProjectMapper UPDATE_PROJECT_MAPPER = UpdateProjectMapper.getInstance();
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

    public void createProject(CreateProjectDto projectDto) {
        if (PROJECT_VALIDATOR.isValid(projectDto)) {
            Project newProject = PROJECT_MAPPER.mapFrom(projectDto);
            PROJECT_DAO.saveWithHibernate(newProject);
        } else {
            throw new ValidationException("Couldn't validate project");
        }
    }

    public void update(UpdateProjectDto projectDto) {
        var project = UPDATE_PROJECT_MAPPER.mapFrom(projectDto);
        PROJECT_DAO.update(project);
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

    public void deleteProjectById(String id) {
        Integer projectId;
        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new NotFoundException("Incorrect id provided");
        }

        if (!PROJECT_DAO.removeById(projectId)) {
            throw new DataBaseOperationException(
                    String.format("Couldn't delete project with following Id = %d", projectId));
        }
    }

    public Project findProjectById() {
        view.write("Please enter Project's id:");
        Integer projectId = Integer.parseInt(view.read());

        Project project =  PROJECT_DAO.findByIdWithHibernate(projectId).orElseThrow(() ->
                new NotFoundException(
                        String.format("\033[0;91mProject with Id = %d wasn't found\033[0m", projectId)));

        view.write(project.toString());

        return project;
    }

    public CreateProjectDto findProjectById(String id) {
        Integer projectId;
        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        Project project =  PROJECT_DAO.findByIdWithHibernate(projectId).orElseThrow(() ->
                new NotFoundException(
                        String.format("Project with Id = %d wasn't found", projectId)));


        return PROJECT_MAPPER.mapTo(project);
    }

    public UpdateProjectDto getProjectForUpdateById(String id) {
        Integer projectId;
        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        Project project =  PROJECT_DAO.findById(projectId).orElseThrow(() ->
                new NotFoundException(
                        String.format("Project with Id = %d wasn't found", projectId)));


        return UPDATE_PROJECT_MAPPER.mapTo(project);
    }

    public List<Project> findAllProjects() {
        return PROJECT_DAO.findAll();
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

    public List<CreateDeveloperDto> getDevelopersList(String id) {
        Integer projectId;
        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        var devs = PROJECT_DAO.getListOfInvolvedDevelopers(projectId);

        if (devs.size() == 0) {
            return new ArrayList<>();
        } else {
            return devs.stream().map(DEV_MAPPER::mapTo).collect(Collectors.toList());
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

    public double getDevelopersSalary(String id) {
        Integer projectId;
        try {
            projectId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        var salary = PROJECT_DAO.getListOfInvolvedDevelopers(projectId)
                        .stream().map(Developer::getSalary)
                        .reduce(BigDecimal.valueOf(0.0), BigDecimal::add);
        return salary.doubleValue();
    }

    public void getProjectInfo() {
        view.write(PROJECT_DAO.getProjectInfo());
    }

    public List<ProjectInfoDto> getAllProjectsInfo() {
        return PROJECT_DAO.getAllProjectsInfo();
    }
}
