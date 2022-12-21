package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindDeveloperDto {
    String firstName;
    String lastName;
    String sex;
    String companyName;
    String projects;
}
