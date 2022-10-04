package ua.com.goit.command.customer;

import ua.com.goit.command.Command;
import ua.com.goit.service.CustomerService;

public class FindCustomerById implements Command {
    public static final String FIND_CUSTOMER_BY_ID = "find customer by id";
    private static final CustomerService CUSTOMER_SERVICE = CustomerService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return FIND_CUSTOMER_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        CUSTOMER_SERVICE.findCustomerById();
    }
}
