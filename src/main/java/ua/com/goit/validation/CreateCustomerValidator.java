package ua.com.goit.validation;

import ua.com.goit.dto.CreateCustomerDto;

public class CreateCustomerValidator implements Validator<CreateCustomerDto> {
    private final static CreateCustomerValidator VALIDATOR = new CreateCustomerValidator();

    private CreateCustomerValidator() {
    }

    public static CreateCustomerValidator getInstance() {
        return VALIDATOR;
    }


    @Override
    public boolean isValid(CreateCustomerDto customerDto) {
        if (customerDto.getName().isBlank()) return false;
        if (customerDto.getSurname().isBlank()) return false;
        if (customerDto.getAddress().isBlank()) return false;

        return true;
    }
}
