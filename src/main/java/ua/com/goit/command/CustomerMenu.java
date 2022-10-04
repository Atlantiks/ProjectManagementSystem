package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.company.CreateCompany;
import ua.com.goit.command.customer.CreateCustomer;
import ua.com.goit.command.customer.DeleteCustomerById;
import ua.com.goit.command.customer.FindCustomerById;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class CustomerMenu implements Command {
    public static final String CUSTOMER_COMMANDS = "customer service";
    @NonNull private View view;



    @Override
    public boolean canBeExecuted(String input) {
        return CUSTOMER_COMMANDS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("===========CUSTOMER SERVICE MENU===========");
        view.write(String.format("Type %s to add new customer", "\033[0;93m" + CreateCustomer.CREATE_CUSTOMER + "\033[0m"));
        view.write(String.format("Type %s to look for customer providing his Id", "\033[0;93m" + FindCustomerById.FIND_CUSTOMER_BY_ID + "\033[0m"));
        view.write(String.format("Type %s to remove new customer providing his Id", "\033[0;93m" + DeleteCustomerById.DEL_CUSTOMER_BY_ID + "\033[0m"));

        view.write(String.format("\n .. or type %s to see all available commands","\033[0;93m" + "help" + "\033[0m"));
    }
}
