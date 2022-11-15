package ua.com.goit.mapper;

import ua.com.goit.Formatter;
import ua.com.goit.dto.CreateSkillDto;
import ua.com.goit.entity.Skill;

public class CreateSkillMapper implements Mapper<CreateSkillDto, Skill> {
    private static final CreateSkillMapper CREATE_SKILL_MAPPER = new CreateSkillMapper();

    private CreateSkillMapper() {}

    public static CreateSkillMapper getInstance() {
        return CREATE_SKILL_MAPPER;
    }

    @Override
    public Skill mapFrom(CreateSkillDto skillDto) {
        return Skill.builder()
                .name(Formatter.capitalize(skillDto.getName()))
                .level(Formatter.capitalize(skillDto.getLevel()))
                .build();
    }

    @Override
    public CreateSkillDto mapTo(Skill object) {
        return null;
    }
}
