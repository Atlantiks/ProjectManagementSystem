package ua.com.goit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Project {
    @Id
    private Integer id;
    @NonNull private String name;
    private LocalDate date_created;
    private String description;
    private String status;
    private BigDecimal cost;
}
