package ua.com.goit.entity;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Developer {
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String sex;
    private Integer companyId;
    private BigDecimal salary;
}
