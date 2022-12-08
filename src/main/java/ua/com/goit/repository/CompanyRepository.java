package ua.com.goit.repository;

import lombok.Cleanup;
import org.hibernate.SessionFactory;
import ua.com.goit.entity.Company;

import java.util.Optional;

public class CompanyRepository extends BasicRepository<Integer, Company> {

    public CompanyRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Company.class);
    }

    public Optional<Company> findByName(String name) {
        @Cleanup var session = sessionFactory.openSession();

        return session.createQuery("select c from Company c " +
                        "where c.name = :companyName", Company.class)
                .setParameter("companyName", name)
                .uniqueResultOptional();
    }
}
