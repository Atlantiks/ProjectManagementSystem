package ua.com.goit.command.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CustomerDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class FindCustomerById implements Command {
    public static final String FIND_CUSTOMER_BY_ID = "find customer by id";
    private static final CustomerDao CUSTOMER_DAO = CustomerDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return FIND_CUSTOMER_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter customer's id:");
        Integer customerId = Integer.parseInt(view.read());

        CUSTOMER_DAO.findById(customerId).ifPresentOrElse(
                dev -> view.write(dev.toString()),
                () -> view.write(String.format("\033[0;91mCustomer with Id = %d wasn't found\033[0m", customerId)));
    }
}
