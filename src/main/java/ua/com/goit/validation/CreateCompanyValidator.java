package ua.com.goit.validation;

import ua.com.goit.dto.CreateCompanyDto;

public class CreateCompanyValidator implements Validator<CreateCompanyDto> {
    private final static CreateCompanyValidator VALIDATOR = new CreateCompanyValidator();

    private CreateCompanyValidator() {
    }

    public static CreateCompanyValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(CreateCompanyDto company) {
        if (company.getName().isBlank()) return false;
        if (company.getCountry().isBlank()) return false;

        return true;
    }
}
