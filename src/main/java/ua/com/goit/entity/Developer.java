package ua.com.goit.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class Developer {
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    private String sex;
    private Integer companyId;
    private Double salary;
}
