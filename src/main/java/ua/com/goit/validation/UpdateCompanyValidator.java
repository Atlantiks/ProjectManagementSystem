package ua.com.goit.validation;

import ua.com.goit.dto.UpdateCompanyDto;

public class UpdateCompanyValidator implements Validator<UpdateCompanyDto> {
    private final static UpdateCompanyValidator VALIDATOR = new UpdateCompanyValidator();

    private UpdateCompanyValidator() {
    }

    public static UpdateCompanyValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(UpdateCompanyDto companyDto) {
        if (!companyDto.getName().matches("[0-9A-z\\s]+")) return false;
        if (!companyDto.getCountry().matches("[A-z\\s]+")) return false;

        return true;
    }
}
