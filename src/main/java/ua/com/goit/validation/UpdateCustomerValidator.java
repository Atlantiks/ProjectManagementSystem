package ua.com.goit.validation;

import ua.com.goit.dto.UpdateCustomerDto;
import ua.com.goit.mapper.UpdateCustomerMapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UpdateCustomerValidator implements Validator<UpdateCustomerDto> {
    private final static UpdateCustomerValidator UPDATE_CUSTOMER_VALIDATOR = new UpdateCustomerValidator();

    private UpdateCustomerValidator() {
    }

    public static UpdateCustomerValidator getInstance() {
        return UPDATE_CUSTOMER_VALIDATOR;
    }

    @Override
    public boolean isValid(UpdateCustomerDto customerDto) {
        if (!customerDto.getName().matches("[A-z\\s]+")) return false;
        if (!customerDto.getSurname().matches("[A-z]+")) return false;

        try {
            Integer.parseInt(customerDto.getId());
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
