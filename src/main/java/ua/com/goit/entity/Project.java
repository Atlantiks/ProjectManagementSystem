package ua.com.goit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull private String name;
    private LocalDate date_created;
    private String description;
    private String status;
    private BigDecimal cost;

    @ManyToMany
    @JoinTable(
            name = "projects_developers",
            joinColumns = { @JoinColumn(name = "projects_id") },
            inverseJoinColumns = { @JoinColumn(name = "developers_id") }
    )
    @Builder.Default
    private List<Developer> developers = new ArrayList<>();
}
