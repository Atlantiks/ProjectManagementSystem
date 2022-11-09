package ua.com.goit.validation;

import ua.com.goit.dto.CreateDeveloperDto;

public class CreateDeveloperValidator implements Validator<CreateDeveloperDto> {

    @Override
    public boolean isValid(CreateDeveloperDto object) {
        return false;
    }
}
