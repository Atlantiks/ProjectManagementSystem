package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.developer.CreateDeveloper;
import ua.com.goit.command.developer.FindDeveloperById;
import ua.com.goit.command.developer.GetDevelopersWithSkillLevel;
import ua.com.goit.command.developer.GetDevelopersWithSkillName;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class DeveloperMenu implements Command {
    public static final String DEV_COMMANDS = "developer service";
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return DEV_COMMANDS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("===========DEVELOPER SERVICE MENU===========");
        view.write(String.format("Type %s to add new developer", "\033[0;93m" + CreateDeveloper.CREATE_DEV + "\033[0m"));
        view.write(String.format("Type %s to look for developer with particular Id", "\033[0;93m" + FindDeveloperById.FIND_DEV_BY_ID + "\033[0m"));
        view.write(String.format("Type %s to get all developers with certain skill level",
                "\033[0;93m" + GetDevelopersWithSkillLevel.GET_DEVS_WITH_SKILL + "\033[0m"));
        view.write(String.format("Type %s to get all developers with certain skill or technology knowledge",
                "\033[0;93m" + GetDevelopersWithSkillName.GET_DEVS_WITH_SKILL + "\033[0m"));
        view.write(String.format("\n .. or type %s to see all available commands","\033[0;93m" + "help" + "\033[0m"));
    }
}
