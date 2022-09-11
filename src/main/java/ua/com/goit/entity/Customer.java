package ua.com.goit.entity;

import lombok.Data;

@Data
public class Customer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
}
