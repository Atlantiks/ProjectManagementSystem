package ua.com.goit.repository;

import lombok.Cleanup;
import org.hibernate.SessionFactory;
import ua.com.goit.entity.Project;

import java.util.Objects;
import java.util.Optional;

public class ProjectRepository extends BasicRepository<Integer, Project> {

    public ProjectRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Project.class);
    }

    public Optional<Project> getProjectWithDevelopers(Integer id) {
        if (Objects.isNull(id)) return Optional.empty();
        @Cleanup var session = sessionFactory.openSession();
        var project = session.find(clazz, id);
        if (!Objects.isNull(project)) {
            var devs = project.getDevelopers().size();
        }

        return Optional.ofNullable(project);
    }
}
