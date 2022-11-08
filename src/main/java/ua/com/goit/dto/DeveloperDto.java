package ua.com.goit.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {
    private String fullName;
    private String sex;
    private BigDecimal salary;
}
