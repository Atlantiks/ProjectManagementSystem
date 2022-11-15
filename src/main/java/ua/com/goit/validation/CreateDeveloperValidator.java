package ua.com.goit.validation;

import ua.com.goit.dto.CreateDeveloperDto;

public class CreateDeveloperValidator implements Validator<CreateDeveloperDto> {
    private final static CreateDeveloperValidator VALIDATOR = new CreateDeveloperValidator();

    private CreateDeveloperValidator() {
    }

    public static CreateDeveloperValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(CreateDeveloperDto developerDto) {
        if (!developerDto.getFirstName().matches("[A-z\\s]+")) return false;
        if (!developerDto.getLastName().matches("[A-z]+")) return false;
        if (!developerDto.getSex().matches("[FM]")) return false;

        if (!developerDto.getCompanyId().isEmpty() &&
                !developerDto.getCompanyId().matches("\\d+")) {
            return false;
        }

        return true;
    }
}
