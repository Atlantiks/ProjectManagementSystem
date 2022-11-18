package ua.com.goit.dto.assignSkill;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AssignSkillDto {
    String developer;
    String skill;
}
