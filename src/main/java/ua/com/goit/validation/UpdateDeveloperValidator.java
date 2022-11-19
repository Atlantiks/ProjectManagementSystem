package ua.com.goit.validation;

import ua.com.goit.dto.UpdateDeveloperDto;

public class UpdateDeveloperValidator implements Validator<UpdateDeveloperDto> {
    private final static UpdateDeveloperValidator VALIDATOR = new UpdateDeveloperValidator();

    private UpdateDeveloperValidator() {
    }

    public static UpdateDeveloperValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(UpdateDeveloperDto developerDto) {
        if (!developerDto.getName().matches("[A-z\\s]+")) return false;
        if (!developerDto.getSurname().matches("[A-z]+")) return false;
        if (!developerDto.getSex().matches("[FM]")) return false;
        if (!developerDto.getSalary().matches("\\d+")) return false;

        return true;
    }
}
