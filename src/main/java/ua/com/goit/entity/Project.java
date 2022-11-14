package ua.com.goit.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Project {
    private Integer id;
    @NonNull private String name;
    private LocalDate date_created;
    private String description;
    private String status;
    private BigDecimal cost;
}
