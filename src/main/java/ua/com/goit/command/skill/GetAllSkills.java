package ua.com.goit.command.skill;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Skill;
import ua.com.goit.view.View;

@RequiredArgsConstructor
public class GetAllSkills implements Command {
    public static final String GET_SKILLS = "get skills";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @NonNull private View view;

    @Override
    public boolean canBeExecuted(String input) {
        return GET_SKILLS.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        SKILL_DAO.findAll().stream()
                .map(Skill::getName)
                .distinct()
                .map(String::valueOf)
                .forEach(view::write);
    }
}
