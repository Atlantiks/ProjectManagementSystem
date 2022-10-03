package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class SkillMenu implements Command {
    public static final String SKILL_COMMANDS = "skill service";
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return SKILL_COMMANDS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("In skill menu..");
    }
}
