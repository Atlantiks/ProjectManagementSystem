package ua.com.goit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Project {
    private Integer id;
    @NonNull private String name;
    private String description;
    private String status;
    private BigDecimal cost;
}
