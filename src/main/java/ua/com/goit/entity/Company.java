package ua.com.goit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Company {
    @Id
    private Integer id;
    @NonNull private String name;
    private String country;
}
