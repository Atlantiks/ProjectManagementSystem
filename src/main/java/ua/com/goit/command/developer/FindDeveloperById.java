package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class FindDeveloperById implements Command {
    public static final String FIND_DEV_BY_ID = "find developer by id";
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return FIND_DEV_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter developer's id:");
        Integer devId = Integer.parseInt(view.read());

        DEV_DAO.findById(devId).ifPresentOrElse(
                dev -> view.write(dev.toString()),
                () -> view.write(String.format("\033[0;91mDeveloper with Id = %d wasn't found\033[0m", devId)));
    }
}
