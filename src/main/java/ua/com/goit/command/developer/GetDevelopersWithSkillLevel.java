package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetDevelopersWithSkillLevel implements Command {
    private static final String CREATE_DEV = "get developers with skill level ";
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return false;
    }

    @Override
    public void execute() {

    }
}
