package ua.com.goit.command.developer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.DeveloperService;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetDevelopersWithSkillLevel implements Command {
    public static final String GET_DEVS_WITH_SKILL = "get developers with skill level";
    private final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_WITH_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        DEVELOPER_SERVICE.getDevelopersWithSkillLevel();
    }
}
