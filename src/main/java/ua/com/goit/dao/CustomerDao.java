package ua.com.goit.dao;

import ua.com.goit.entity.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDao implements DataAccess<Integer, Customer> {

    @Override
    public Optional<Customer> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public boolean remove(Customer customer) {
        return false;
    }

    @Override
    public boolean removeById(Integer integer) {
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }
}
