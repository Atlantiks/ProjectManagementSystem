package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSkillDto {
    String name;
    String level;
}
