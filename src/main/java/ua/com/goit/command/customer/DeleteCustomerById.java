package ua.com.goit.command.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CustomerDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeleteCustomerById implements Command {
    public static final String DEL_CUSTOMER_BY_ID = "delete customer by id";
    private static final CustomerDao CUSTOMER_DAO = CustomerDao.getInstance();
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return DEL_CUSTOMER_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter customer's id:");
        Integer customerId = Integer.parseInt(view.read());

        if (CUSTOMER_DAO.removeById(customerId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete customer with following Id = %d", customerId));
        }
    }
}
