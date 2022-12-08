package ua.com.goit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String sex;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private BigDecimal salary;

    @ManyToMany(mappedBy = "developers")
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
}
