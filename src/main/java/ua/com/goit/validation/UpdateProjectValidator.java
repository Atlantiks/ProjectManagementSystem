package ua.com.goit.validation;

import ua.com.goit.dto.UpdateProjectDto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UpdateProjectValidator implements Validator<UpdateProjectDto> {

    @Override
    public boolean isValid(UpdateProjectDto projectDto) {
        List<String> statuses = List.of("Active", "Inactive", "Discontinued", "Not commissioned");
        if (!statuses.contains(projectDto.getStatus())) return false;

        if (!projectDto.getName().matches("[A-z]+")) return false;
        if (!projectDto.getDescription().isBlank()) return false;
        try {
            Integer.parseInt(projectDto.getId());
            LocalDate.parse(projectDto.getDate());
        } catch (DateTimeParseException | NumberFormatException e) {
            return false;
        }

        return true;
    }
}
