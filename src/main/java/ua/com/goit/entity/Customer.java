package ua.com.goit.entity;

import lombok.Data;

@Data
public class Customer {
    Integer id;
    String firstName;
    String lastName;
    String company;
    String address;
}
