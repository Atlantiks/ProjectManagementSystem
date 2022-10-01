package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.view.View;

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
        System.out.println();
        String firstName = view.read();
        System.out.println();
        String lastName = view.read();
        System.out.println();
        String sex = view.read();

        Developer newDev = new Developer(firstName,lastName,sex);

        Developer savedDev = DEV_DAO.save(newDev);
        System.out.println(savedDev);
    }
}
