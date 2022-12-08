package ua.com.goit.repository;

import org.hibernate.SessionFactory;
import ua.com.goit.entity.Developer;

public class DeveloperRepository extends BasicRepository<Integer, Developer> {

    public DeveloperRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Developer.class);
    }
}
