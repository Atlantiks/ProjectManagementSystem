package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.*;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DataAccess<Integer, Developer> devDao = new DeveloperDao(connectionManager.getConnection());
        DataAccess<Integer, Project> projectDao = new ProjectDao(connectionManager.getConnection());
        DataAccess<Integer, Company> companyDao = new CompanyDao(connectionManager.getConnection());
        DataAccess<Integer, Customer> customerDao = new CustomerDao(connectionManager.getConnection());

        companyDao.findAll().forEach(System.out::println);

        Company comp = new Company("Shynkarenko");
        comp.setCountry("Ukraine");
        comp.setId(9);
        companyDao.update(comp);


        companyDao.findAll().forEach(System.out::println);

    }

}
