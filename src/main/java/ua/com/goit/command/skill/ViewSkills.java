package ua.com.goit.command.skill;

import ua.com.goit.command.Command;
import ua.com.goit.service.SkillService;

public class ViewSkills implements Command {
    public static final String VIEW_SKILLS = "view skills";
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();

    @Override
    public boolean canBeExecuted(String input) {
        return VIEW_SKILLS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        SKILL_SERVICE.viewAllSkills();
    }

}
