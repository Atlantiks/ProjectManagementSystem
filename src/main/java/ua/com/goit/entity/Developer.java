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

    @ManyToMany(mappedBy = "developers", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Project> projects = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "developers_skills",
            joinColumns = { @JoinColumn(name = "developers_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
    @Builder.Default
    private List<Skill> skills = new ArrayList<>();
}
