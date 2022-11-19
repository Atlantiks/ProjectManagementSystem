package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateCompanyDto {
    String id;
    String name;
    String country;
}
