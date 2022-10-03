package ua.com.goit.command.skill;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.command.Command;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Skill;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CreateSkill implements Command {
    public static final String CREATE_SKILL = "create skill";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    @NonNull private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return CREATE_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("To create new Skill it's compulsory to enter skill name and skill level");

        view.write("1. Please enter new Skill's name:");
        String skillName = view.read();
        if (skillName.isBlank()) throw new BlancFieldException();
        view.write("2. Please enter new Skill's level or press ENTER to add default structure (junior, middle, senior");
        String skillLevel = view.read();

        if (skillLevel.isBlank()) {
            List<Skill> newSkills = new ArrayList<>();
            newSkills.add(new Skill(skillName, "Junior"));
            newSkills.add(new Skill(skillName, "Middle"));
            newSkills.add(new Skill(skillName, "Senior"));
            newSkills.stream().peek(skill -> SKILL_DAO.save(skill, view)).forEach(skill -> view.write(skill.toString()));
        } else {
            var newSkill = new Skill(skillName, skillLevel);
            SKILL_DAO.save(newSkill, view);
        }
    }
}
