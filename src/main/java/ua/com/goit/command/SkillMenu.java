package ua.com.goit.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.skill.AssignNewSkillToDeveloper;
import ua.com.goit.command.skill.CreateSkill;
import ua.com.goit.command.skill.GetAllSkills;
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
        view.write("===========SKILL SERVICE MENU===========");
        view.write(String.format("Type %s to add new skill", "\033[0;93m" + CreateSkill.CREATE_SKILL + "\033[0m"));
        view.write(String.format("Type %s to assign new skill to existing developer", "\033[0;93m" + AssignNewSkillToDeveloper.ASSIGN_SKILL + "\033[0m"));
        view.write(String.format("Type %s to list all available skills/technologies", "\033[0;93m" + GetAllSkills.GET_SKILLS + "\033[0m"));

        view.write(String.format("\n .. or type %s to see all available commands","\033[0;93m" + "help" + "\033[0m"));
    }
}
