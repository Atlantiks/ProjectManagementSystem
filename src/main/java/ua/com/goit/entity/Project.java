package ua.com.goit.entity;

import lombok.Data;

@Data
public class Project {
    Integer id;
    String name;
    String description;
    String status;
    Double cost;
}
