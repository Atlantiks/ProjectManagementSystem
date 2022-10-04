package ua.com.goit.command.skill;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.com.goit.Formatter;
import ua.com.goit.command.Command;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Skill;
import ua.com.goit.view.View;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AssignNewSkillToDeveloper implements Command {
    public static final String ASSIGN_SKILL = "assign skill to developer";
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();
    private static final DeveloperDao DEV_DAO = DeveloperDao.getInstance();
    @NonNull
    private View view;


    @Override
    public boolean canBeExecuted(String input) {
        return ASSIGN_SKILL.equalsIgnoreCase(input);
    }

    @Override
    public void execute() {
        view.write("Please enter developer's id:");
        Integer devId = Integer.parseInt(view.read());

        Optional<Developer> devById = DEV_DAO.findById(devId);

        if (devById.isPresent()) {
            view.write("Following developer was found by id = " + devId);
            view.write(devById.get().toString());
        } else {
            view.write(String.format("\033[0;91mDeveloper with Id = %d wasn't found\033[0m", devId));
            return;
        }

        view.write("Please enter developer's new skill (choose from list underneath) :");
        List<Skill> allSkills = SKILL_DAO.findAll();

        String listOfAllSkills = allSkills.stream()
                .map(Skill::getName)
                .distinct()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        view.write(listOfAllSkills);

        String devNewSkill = Formatter.capitalize(view.read());
        Integer newSkillId = null;

        if (listOfAllSkills.contains(devNewSkill)) {
            view.write("Enter skill level");
            String skillLevel = view.read();

            for (var skill : allSkills) {
                if (skill.getName().equalsIgnoreCase(devNewSkill) &&
                        skill.getLevel().equalsIgnoreCase(skillLevel)) {
                    newSkillId = skill.getId();
                    break;
                }
            }

            if (Objects.isNull(newSkillId)) {
                view.write(String.format("Entered skill %s with %s level doesn't exist in database. Add skill first.",
                        devNewSkill, skillLevel));
            } else {
                view.write(String.format("Creating skill %s-%s for user %s %s...",
                        devNewSkill, skillLevel, devById.get().getFirstName(), devById.get().getLastName()));
                if (SKILL_DAO.assignSkillToDev(devId, newSkillId)) {
                    view.write("Success");
                } else {
                    view.write("ERROR occurred");
                }
            }
        } else {
            view.write(String.format("Entered skill %s doesn't exist in database. Add skill first.", devNewSkill));
        }

    }
}
