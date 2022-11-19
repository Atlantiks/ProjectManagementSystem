package ua.com.goit.validation;

import ua.com.goit.dto.UpdateCustomerDto;
import ua.com.goit.mapper.UpdateCustomerMapper;

public class UpdateCustomerValidator implements Validator<UpdateCustomerDto> {
    private final static UpdateCustomerValidator UPDATE_CUSTOMER_VALIDATOR = new UpdateCustomerValidator();

    private UpdateCustomerValidator() {
    }

    public static UpdateCustomerValidator getInstance() {
        return UPDATE_CUSTOMER_VALIDATOR;
    }

    @Override
    public boolean isValid(UpdateCustomerDto object) {
        return false;
    }
}
