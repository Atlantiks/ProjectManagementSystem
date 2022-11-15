package ua.com.goit.entity;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Company {
    private Integer id;
    @NonNull private String name;
    private String country;
}
