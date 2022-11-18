package ua.com.goit.dto.assignSkill;

import lombok.Builder;
import lombok.Value;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Skill;

import java.util.List;

@Value
@Builder
public class ChooseSkillLevelForDeveloperDto {
    Developer developer;
    List<Skill> availableSkills;
}
