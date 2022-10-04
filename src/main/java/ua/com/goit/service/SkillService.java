package ua.com.goit.service;

import lombok.Getter;
import lombok.Setter;
import ua.com.goit.Formatter;
import ua.com.goit.dao.SkillDao;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Skill;
import ua.com.goit.exception.BlancFieldException;
import ua.com.goit.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SkillService {
    private static final SkillService SKILL_SERVICE = new SkillService();
    private static final DeveloperService DEV_SERVICE = DeveloperService.getInstance();
    private static final SkillDao SKILL_DAO = SkillDao.getInstance();

    @Getter
    @Setter
    private View view;

    private SkillService() {
    }

    public static SkillService getInstance() {
        return SKILL_SERVICE;
    }


    public void createSkill() {
        view.write("To create new Skill it's compulsory to enter skill name and skill level");

        view.write("1. Please enter new Skill's name:");
        String skillName = view.read();
        if (skillName.isBlank()) {
            throw new BlancFieldException();
        } else {
            skillName = Formatter.capitalize(skillName);
        }
        view.write("2. Please enter new Skill's level or press ENTER to add default structure (Junior, Middle, Senior)");
        String skillLevel = view.read();

        if (skillLevel.isBlank()) {
            List<Skill> newSkills = new ArrayList<>();
            List<Skill> createdSkills = new ArrayList<>();

            newSkills.add(new Skill(skillName, "Junior"));
            newSkills.add(new Skill(skillName, "Middle"));
            newSkills.add(new Skill(skillName, "Senior"));

            newSkills.stream()
                    .peek(skill -> SKILL_DAO.save(skill, view))
                    .filter(skill -> Objects.nonNull(skill.getId()))
                    .forEach(createdSkills::add);

            if (createdSkills.size() > 0) {
                view.write("Following entries were added to database:");
                createdSkills.stream().map(String::valueOf).forEach(view::write);
            }
        } else {
            var newSkill = new Skill(skillName, skillLevel);
            var createdSkill = SKILL_DAO.save(newSkill, view);
            view.write(createdSkill.toString());
        }
    }

    public void viewAllSkills() {
        view.write(getSkillsList());
    }

    public void assignNewSkillToDeveloper() {
        Developer developer = DEV_SERVICE.findDeveloperById();
        List<Skill> allSkills = SKILL_DAO.findAll();
        String listOfAllSkills = getSkillsList();

        view.write("Please enter developer's new skill (choose from list underneath) :");
        view.write(listOfAllSkills);

        String devNewSkill = Formatter.capitalize(view.read());

        if (listOfAllSkills.contains(devNewSkill)) {
            Integer newSkillId = null;
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
                view.write(String.format(
                        "Entered skill %s with %s level doesn't exist in database. Add skill first.",
                        devNewSkill, skillLevel));
            } else {
                view.write(String.format(
                        "Creating skill %s-%s for user %s %s...",
                        devNewSkill, skillLevel, developer.getFirstName(), developer.getLastName()));

                if (SKILL_DAO.assignSkillToDev(developer.getId(), newSkillId)) {
                    view.write(String.format(
                            "Skill %s-%s was successfully added to developer %s %s",
                            devNewSkill, skillLevel, developer.getFirstName(), developer.getLastName()));
                } else {
                    view.write("ERROR occurred");
                }

            }

        } else {
            view.write(String.format("Entered skill %s doesn't exist in database. Add skill first.", devNewSkill));
        }
    }

    private String getSkillsList() {
        return SKILL_DAO.findAll().stream()
                .map(Skill::getName)
                .distinct()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
