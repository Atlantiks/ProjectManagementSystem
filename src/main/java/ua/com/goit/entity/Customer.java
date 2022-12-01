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
public class Customer {
    @Id
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    private String company;
    private String address;
}
