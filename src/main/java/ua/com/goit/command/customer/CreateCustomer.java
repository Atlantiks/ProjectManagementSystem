package ua.com.goit.command.customer;

import ua.com.goit.command.Command;
import ua.com.goit.service.CustomerService;

public class CreateCustomer implements Command {
    public static final String CREATE_CUSTOMER = "create customer";
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_CUSTOMER.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        CUSTOMER_SERVICE.createCustomer();
    }
}
