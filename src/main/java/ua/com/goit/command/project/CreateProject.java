package ua.com.goit.command.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.Formatter;
import ua.com.goit.command.Command;
import ua.com.goit.dao.ProjectDao;
import ua.com.goit.entity.Project;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.service.ProjectService;
import ua.com.goit.view.View;

import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateProject implements Command {
    public static final String CREATE_PROJECT = "create project";
    private static final ProjectService PROJECT_SERVICE = ProjectService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_PROJECT.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        PROJECT_SERVICE.createProject();
    }
}
