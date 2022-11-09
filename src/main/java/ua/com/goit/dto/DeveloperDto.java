package ua.com.goit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {
    private String fullName;
    private String sex;
}
