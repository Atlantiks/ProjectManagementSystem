package ua.com.goit.command.skill;

import ua.com.goit.command.Command;
import ua.com.goit.service.SkillService;

public class GetAllSkills implements Command {
    public static final String GET_SKILLS = "get skills";
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return GET_SKILLS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        SKILL_SERVICE.getAllSkills();
    }

}
