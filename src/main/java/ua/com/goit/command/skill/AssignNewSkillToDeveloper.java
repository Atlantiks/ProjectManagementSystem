package ua.com.goit.command.skill;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class AssignNewSkillToDeveloper implements Command {
    public static final String ASSIGN_SKILL = "assign skill to developer";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return ASSIGN_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Under construction...");
    }
}
