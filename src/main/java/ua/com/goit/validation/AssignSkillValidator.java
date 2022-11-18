package ua.com.goit.validation;

import ua.com.goit.dto.assignSkill.AssignSkillDto;
import ua.com.goit.service.SkillService;

import java.util.Objects;

public class AssignSkillValidator implements Validator<AssignSkillDto> {
    private final static AssignSkillValidator VALIDATOR = new AssignSkillValidator();
    private static final SkillService SKILL_SERVICE = SkillService.getInstance();


    private AssignSkillValidator() {
    }

    public static AssignSkillValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(AssignSkillDto assignSkillDto) {
        if (assignSkillDto.getDeveloper().isBlank()) return false;
        if (assignSkillDto.getSkill().isBlank()) return false;

        var devSkill = SKILL_SERVICE.getSkillsOfDeveloper(assignSkillDto).stream()
                .filter(skill -> skill.getName().equalsIgnoreCase(assignSkillDto.getSkill()))
                .findAny().orElse(null);

        return Objects.isNull(devSkill);
    }
}
