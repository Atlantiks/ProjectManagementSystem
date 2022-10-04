package ua.com.goit.command.developer;

import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.service.DeveloperService;

@RequiredArgsConstructor
public class GetDevelopersWithSkillName implements Command {
    public static final String GET_DEVS_WITH_SKILL = "get developers with knowledge of";
    private final DeveloperService DEVELOPER_SERVICE = DeveloperService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return GET_DEVS_WITH_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        DEVELOPER_SERVICE.getDevelopersWithSkillName();
    }
}
