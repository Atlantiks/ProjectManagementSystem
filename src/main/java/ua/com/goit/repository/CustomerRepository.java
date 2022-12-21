package ua.com.goit.repository;

import org.hibernate.SessionFactory;
import ua.com.goit.entity.Customer;

public class CustomerRepository extends BasicRepository<Integer, Customer> {

    public CustomerRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }
}
