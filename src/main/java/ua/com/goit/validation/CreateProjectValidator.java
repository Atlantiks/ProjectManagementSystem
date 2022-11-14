package ua.com.goit.validation;

import ua.com.goit.dto.CreateProjectDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateProjectValidator implements Validator<CreateProjectDto> {
    private final static CreateProjectValidator VALIDATOR = new CreateProjectValidator();

    private CreateProjectValidator() {
    }

    public static CreateProjectValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(CreateProjectDto projectDto) {
        if (projectDto.getName().isBlank()) return false;
        if (projectDto.getDescription().isBlank()) return false;
        if (projectDto.getDate().isBlank()) return false;
        if (projectDto.getStatus().isBlank()) return false;

        try {
           var projectDate= LocalDate.parse(projectDto.getDate());
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
