package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.CustomerDao;
import ua.com.goit.dto.CreateCustomerDto;
import ua.com.goit.dto.UpdateCustomerDto;
import ua.com.goit.entity.Company;
import ua.com.goit.entity.Customer;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.exception.DataBaseOperationException;
import ua.com.goit.exception.NotFoundException;
import ua.com.goit.exception.ValidationException;
import ua.com.goit.mapper.CreateCustomerMapper;
import ua.com.goit.mapper.UpdateCustomerMapper;
import ua.com.goit.validation.CreateCustomerValidator;
import ua.com.goit.validation.UpdateCustomerValidator;
import ua.com.goit.view.View;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerService {
    private static final CustomerService CUSTOMER_SERVICE = new CustomerService();
    private static final CustomerDao CUSTOMER_DAO = CustomerDao.getInstance();
    private final static CreateCustomerValidator CUSTOMER_VALIDATOR = CreateCustomerValidator.getInstance();
    private final static UpdateCustomerValidator UPDATE_CUSTOMER_VALIDATOR = UpdateCustomerValidator.getInstance();
    private static final CreateCustomerMapper CREATE_CUSTOMER_MAPPER = CreateCustomerMapper.getInstance();
    private static final UpdateCustomerMapper UPDATE_CUSTOMER_MAPPER = UpdateCustomerMapper.getInstance();
    @Getter
    @Setter
    private View view;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        return CUSTOMER_SERVICE;
    }

    public void createCustomer() {
        view.write("To create new customer you have to enter required fields and (optionally) non-required ones.");

        view.write("1. Please enter new Customer's name:");
        String firstName = view.read();
        if (firstName.isBlank()) {
            throw new BlancFieldException();
        } else {
            firstName = Formatter.capitalize(firstName);
        }
        view.write("2. Please enter new Customer's surname:");
        String lastName = view.read();
        if (lastName.isBlank()) {
            throw new BlancFieldException();
        } else {
            lastName = Formatter.capitalize(lastName);
        }

        Customer newCustomer = new Customer(firstName, lastName);

        view.write("Would you like to add non-required fields? Y/N  Press ENTER for default option(N)");

        String userAnswer = view.read();
        userAnswer = userAnswer.isBlank() ? "N" : userAnswer.substring(0, 1).toUpperCase();

        switch (userAnswer) {
            case "Y":
                view.write("3. Please enter new Customer's company name:");
                String companyName = view.read();
                if (companyName.isBlank()) {
                    throw new BlancFieldException();
                } else {
                    companyName = Formatter.capitalize(companyName);
                }
                view.write("4. Please enter new Customer's address:");
                String address = view.read();
                if (address.isBlank()) {
                    throw new BlancFieldException();
                } else {
                    address = Formatter.capitalize(address);
                }

                newCustomer.setCompany(companyName);
                newCustomer.setAddress(address);
                break;
            case "N":
                break;
            default:
                view.write("Answer not recognised. Default parameters will be applied");
                break;
        }

        Customer savedCustomer = CUSTOMER_DAO.save(newCustomer, view);

        if (Objects.nonNull(savedCustomer.getId())) {
            view.write("\033[0;92mThe following Customer was successfully added to database:\033[0m");
            view.write(savedCustomer + "\n");
        }
    }

    public void createCustomer(CreateCustomerDto customerDto) {
        if (CUSTOMER_VALIDATOR.isValid(customerDto)) {
            Customer newCustomer = CREATE_CUSTOMER_MAPPER.mapFrom(customerDto);
            CUSTOMER_DAO.saveWithHibernate(newCustomer);
        } else {
            throw new ValidationException("Validation failed");
        }
    }

    public void deleteCustomerById() {
        view.write("Please enter customer's id:");
        Integer customerId = Integer.parseInt(view.read());

        if (CUSTOMER_DAO.removeById(customerId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete customer with following Id = %d", customerId));
        }
    }

    public void deleteCustomerById(String id) {
        Integer customerId;
        try {
            customerId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        if (!CUSTOMER_DAO.removeById(customerId)) {
            throw new DataBaseOperationException(
                    String.format("Couldn't delete customer with following Id = %d", customerId));
        }
    }

    public Customer findCustomerById() {
        view.write("Please enter customer's id:");
        Integer customerId = Integer.parseInt(view.read());

        Customer customer = CUSTOMER_DAO.findById(customerId).orElseThrow(() ->
                new NotFoundException(
                        String.format("\033[0;91mCustomer with Id = %d wasn't found\033[0m", customerId)));

        view.write(customer.toString());

        return customer;
    }

    public void update(UpdateCustomerDto customerDto) {
        if (!UPDATE_CUSTOMER_VALIDATOR.isValid(customerDto)) throw new ValidationException("Validation failed");
        CUSTOMER_DAO.update(UPDATE_CUSTOMER_MAPPER.mapFrom(customerDto));
    }

    public CreateCustomerDto findCustomerById(String id) {
        Integer customerId;
        try {
            customerId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        Customer customer = CUSTOMER_DAO.findByIdWithHibernate(customerId).orElseThrow(() ->
                new NotFoundException(
                        String.format("Customer with Id = %d wasn't found", customerId)));

        return CREATE_CUSTOMER_MAPPER.mapTo(customer);
    }

    public UpdateCustomerDto getCustomerForUpdate(String id) {
        Integer customerId;
        try {
            customerId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Incorrect id provided");
        }

        Customer customer =  CUSTOMER_DAO.findById(customerId).orElseThrow(() ->
                new NotFoundException(
                        String.format("Customer with Id = %d wasn't found", customerId)));


        return UPDATE_CUSTOMER_MAPPER.mapTo(customer);
    }

    public List<CreateCustomerDto> findAllCustomers() {
        return CUSTOMER_DAO.findAll().stream().map(CREATE_CUSTOMER_MAPPER::mapTo).collect(Collectors.toList());
    }
}
