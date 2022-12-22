package ua.com.goit.repository;

import lombok.Cleanup;
import org.hibernate.SessionFactory;
import ua.com.goit.entity.Developer;
import ua.com.goit.exception.NotFoundException;

public class DeveloperRepository extends BasicRepository<Integer, Developer> {

    public DeveloperRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Developer.class);
    }

    public Developer findByName(String name) {
        @Cleanup var session = sessionFactory.openSession();

        var developer = session.createQuery("from Developer d " +
                        "where concat(d.firstName, ' ', d.lastName) = :developerFullName", Developer.class)
                .setParameter("developerFullName", name)
                .uniqueResultOptional().orElseThrow(() -> new NotFoundException("developer not found"));

        var skillsCount = developer.getSkills().size();

        return developer;
    }
}
