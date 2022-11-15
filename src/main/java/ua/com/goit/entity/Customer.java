package ua.com.goit.entity;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    private String company;
    private String address;
}
