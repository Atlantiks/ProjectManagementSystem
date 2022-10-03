package ua.com.goit.command.developer;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateDeveloper implements Command {
    public static final String CREATE_DEV = "create developer";
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_DEV.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("To create new developer you have to enter required fields and (optionally) non-required ones.");

        view.write("1. Please enter new Developer's name:");
        String firstName = view.read();
        if (firstName.isBlank()) throw new BlancFieldException();
        view.write("2. Please enter new Developer's surname:");
        String lastName = view.read();
        if (lastName.isBlank()) throw new BlancFieldException();
        view.write("3. Please enter new Developer's sex (M/F):");
        String sex = view.read();
        if (sex.isBlank()) throw new BlancFieldException();
        sex = sex.toUpperCase().substring(0, 1);

        Developer newDev = new Developer(firstName, lastName, sex);

        view.write("Would you like to add non-required fields? Y/N");

        String userAnswer = view.read();
        userAnswer = userAnswer.isBlank() ? "N" : userAnswer.substring(0, 1).toUpperCase();

        switch (userAnswer) {
            case "Y":
                view.write("4. Please enter new Developer's company id:");
                Integer companyId = Integer.parseInt(view.read());
                view.write("5. Please enter new Developer's salary:");
                BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(view.read()));

                newDev.setCompanyId(companyId);
                newDev.setSalary(salary);
                break;
            case "N":
                break;
            default:
                view.write("Answer not recognised. Default parameters will be applied");
                break;
        }

        Developer savedDev = DEV_DAO.save(newDev, view);

        if (Objects.nonNull(savedDev.getId())) {
            view.write("\033[0;92mThe following developer was successfully added to database:\033[0m");
            view.write(savedDev + "\n");
        }
    }
}
