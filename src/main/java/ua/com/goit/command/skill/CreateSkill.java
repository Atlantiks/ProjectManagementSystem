package ua.com.goit.command.skill;

import ua.com.goit.command.Command;
import ua.com.goit.service.SkillService;

public class CreateSkill implements Command {
    public static final String CREATE_SKILL = "create skill";
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        SKILL_SERVICE.createSkill();
    }
}
