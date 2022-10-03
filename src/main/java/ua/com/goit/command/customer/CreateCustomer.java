package ua.com.goit.command.customer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.Formatter;
import ua.com.goit.command.Command;
import ua.com.goit.dao.CompanyDao;
import ua.com.goit.dao.CustomerDao;
import ua.com.goit.entity.Customer;
import ua.com.goit.entity.Developer;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateCustomer implements Command {
    public static final String CREATE_CUSTOMER = "create customer";
    private static final CustomerDao CUSTOMER_DAO = CustomerDao.getInstance();
    @NonNull
    private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_CUSTOMER.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
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
}
