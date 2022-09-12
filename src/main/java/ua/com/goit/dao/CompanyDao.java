package ua.com.goit.dao;

import ua.com.goit.entity.Company;

import java.util.List;
import java.util.Optional;

public class CompanyDao implements DataAccess<Integer, Company>{

    @Override
    public Optional<Company> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Company save(Company company) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return null;
    }

    @Override
    public boolean remove(Company company) {
        return false;
    }

    @Override
    public boolean removeById(Integer integer) {
        return false;
    }

    @Override
    public boolean update(Company company) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }
}
