package ua.com.goit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Customer {
    private Integer id;
    @NonNull private String firstName;
    @NonNull private String lastName;
    private String company;
    private String address;
}
