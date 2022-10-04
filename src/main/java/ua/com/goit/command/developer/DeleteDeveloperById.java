package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeleteDeveloperById implements Command {
    public static final String DEL_DEV_BY_ID = "delete developer by id";
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return DEL_DEV_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter developer's id:");
        Integer devId = Integer.parseInt(view.read());

        if (DEV_DAO.removeById(devId)) {
            view.write("Success!");
        } else {
            view.write(String.format("Couldn't delete developer with following Id = %d", devId ));
        }
    }
}
