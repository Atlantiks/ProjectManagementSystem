package ua.com.goit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String sex;
    private Integer companyId;
    private BigDecimal salary;
}
