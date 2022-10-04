package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeleteDeveloperById implements Command {
    public static final String DEL_DEV_BY_ID = "delete developer by id";
    private final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return DEL_DEV_BY_ID.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        DEVELOPER_SERVICE.deleteDeveloperById();
    }
}
