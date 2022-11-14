package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProjectDto {
    String name;
    String description;
    String date;
    String status;
}
