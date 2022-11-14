package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectInfoDto {
    String date;
    String name;
    String numberOfDevelopers;
}
