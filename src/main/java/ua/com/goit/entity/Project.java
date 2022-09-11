package ua.com.goit.entity;

import lombok.Data;

@Data
public class Project {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private Double cost;
}
