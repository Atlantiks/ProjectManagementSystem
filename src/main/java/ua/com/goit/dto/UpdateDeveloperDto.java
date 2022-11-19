package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateDeveloperDto {
    String id;
    String name;
    String surname;
    String sex;
    String companyName;
    String salary;
}
