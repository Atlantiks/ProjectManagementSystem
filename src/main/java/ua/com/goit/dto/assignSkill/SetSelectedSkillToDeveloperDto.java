package ua.com.goit.dto.assignSkill;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetSelectedSkillToDeveloperDto {
    String developerId;
    String skillId;
}
