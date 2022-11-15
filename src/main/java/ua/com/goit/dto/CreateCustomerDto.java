package ua.com.goit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateCustomerDto {
    String name;
    String surname;
    String company;
    String address;
}
