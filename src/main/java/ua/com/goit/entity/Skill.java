package ua.com.goit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Skill {
    private Integer id;
    @NonNull private String name;
    @NonNull private String level;
}
