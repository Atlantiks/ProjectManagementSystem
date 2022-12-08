package ua.com.goit.repository;

import org.hibernate.SessionFactory;
import ua.com.goit.entity.Project;

public class ProjectRepository extends BasicRepository<Integer, Project> {

    public ProjectRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Project.class);
    }
}
