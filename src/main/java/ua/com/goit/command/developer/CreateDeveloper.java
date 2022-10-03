package ua.com.goit.command.developer;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.view.View;

import java.util.Objects;

@RequiredArgsConstructor
public class CreateDeveloper implements Command {
    public static final String CREATE_DEV = "create developer";
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_DEV.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("To create new developer you have to enter required fields and (optionally) non-required ones.");

        view.write("1. Please enter new Developer's name:");
        String firstName = view.read();
        view.write("2. Please enter new Developer's surname:");
        String lastName = view.read();
        view.write("3. Please enter new Developer's sex (M/F):");
        String sex = view.read();

        Developer newDev = new Developer(firstName,lastName,sex);

        view.write("Would you like to add non-required fields? Y/N");

        Developer savedDev = DEV_DAO.save(newDev, view);

        if (Objects.nonNull(savedDev.getId())) view.write(savedDev.toString());

        view.write("\nPlease, enter next command...");
    }
}
