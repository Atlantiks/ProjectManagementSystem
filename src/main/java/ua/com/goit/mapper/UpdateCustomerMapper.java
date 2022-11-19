package ua.com.goit.mapper;

import ua.com.goit.dto.UpdateCustomerDto;
import ua.com.goit.entity.Customer;
import ua.com.goit.service.CompanyService;

import java.util.Objects;

public class UpdateCustomerMapper implements Mapper<UpdateCustomerDto, Customer> {
    private static final UpdateCustomerMapper UPDATE_CUSTOMER_MAPPER = new UpdateCustomerMapper();

    private UpdateCustomerMapper() {}

    public static UpdateCustomerMapper getInstance() {
        return UPDATE_CUSTOMER_MAPPER;
    }


    @Override
    public Customer mapFrom(UpdateCustomerDto customerDto) {
        return Customer.builder()
                .id(Integer.parseInt(customerDto.getId()))
                .firstName(customerDto.getName())
                .lastName(customerDto.getSurname())
                .company(customerDto.getCompany().isBlank() ? null : customerDto.getCompany() )
                .address(customerDto.getAddress())
                .build();
    }

    @Override
    public UpdateCustomerDto mapTo(Customer customer) {
        return UpdateCustomerDto.builder()
                .id(customer.getId().toString())
                .name(customer.getFirstName())
                .surname(customer.getLastName())
                .address(customer.getAddress())
                .company(customer.getCompany())
                .build();
    }
}
