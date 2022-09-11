package ua.com.goit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Developer {
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String sex;
    private Integer companyId;
    private BigDecimal salary;
}
