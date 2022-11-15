package ua.com.goit.validation;

import ua.com.goit.dto.CreateSkillDto;

import java.util.Objects;

public class CreateSkillValidator implements Validator<CreateSkillDto> {
    private final static CreateSkillValidator VALIDATOR = new CreateSkillValidator();

    private CreateSkillValidator() {
    }

    public static CreateSkillValidator getInstance() {
        return VALIDATOR;
    }

    @Override
    public boolean isValid(CreateSkillDto skillDto) {
        if (skillDto.getName().isBlank()) return false;
        if (!skillDto.getName().matches("[A-z\\s]+")) return false;
        if (Objects.nonNull(skillDto.getLevel()) && !skillDto.getLevel().matches("[A-z]+")) return false;

        return true;
    }
}
