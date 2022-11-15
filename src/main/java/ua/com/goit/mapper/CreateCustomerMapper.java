package ua.com.goit.mapper;

import ua.com.goit.Formatter;
import ua.com.goit.dto.CreateCustomerDto;
import ua.com.goit.entity.Customer;

import java.util.Objects;

public class CreateCustomerMapper implements Mapper<CreateCustomerDto, Customer> {
    private static final CreateCustomerMapper CREATE_CUSTOMER_MAPPER = new CreateCustomerMapper();

    private CreateCustomerMapper() {}

    public static CreateCustomerMapper getInstance() {
        return CREATE_CUSTOMER_MAPPER;
    }

    @Override
    public Customer mapFrom(CreateCustomerDto customerDto) {
        return Customer.builder()
                .firstName(Formatter.capitalize(customerDto.getName()))
                .lastName(Formatter.capitalize(customerDto.getSurname()))
                .address(customerDto.getAddress())
                .company(Objects.nonNull(customerDto.getCompany()) ?
                        Formatter.capitalize(customerDto.getCompany()) :
                        customerDto.getCompany())
                .build();
    }

    @Override
    public CreateCustomerDto mapTo(Customer customer) {
        return CreateCustomerDto.builder()
                .name(customer.getFirstName())
                .surname(customer.getLastName())
                .company(Objects.isNull(customer.getCompany()) ? "" : customer.getCompany())
                .address(customer.getAddress())
                .build();
    }
}
