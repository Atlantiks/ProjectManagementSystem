package ua.com.goit.entity;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Skill {
    private Integer id;
    @NonNull private String name;
    @NonNull private String level;
}
