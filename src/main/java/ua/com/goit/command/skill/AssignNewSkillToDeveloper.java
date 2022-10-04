package ua.com.goit.command.skill;

import ua.com.goit.command.Command;
import ua.com.goit.service.SkillService;

public class AssignNewSkillToDeveloper implements Command {
    public static final String ASSIGN_SKILL = "assign skill to developer";
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return ASSIGN_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        SKILL_SERVICE.assignNewSkillToDeveloper();
    }
}
