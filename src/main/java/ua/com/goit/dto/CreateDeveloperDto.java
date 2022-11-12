package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateDeveloperDto {
    String firstName;
    String lastName;
    String sex;
    String companyId;
    String salary;
}
