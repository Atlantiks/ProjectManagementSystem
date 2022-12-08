package ua.com.goit.repository;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BasicRepository<Key, Entity> implements Repository<Key, Entity> {
    protected final SessionFactory sessionFactory;
    protected final Class<Entity> clazz;

    @Override
    public Entity save(Entity entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public void delete(Key id) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(session.find(clazz, id));
        session.getTransaction().commit();
    }

    @Override
    public void update(Entity entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
    }

    @Override
    public Optional<Entity> findById(Key id) {
        if (Objects.isNull(id)) return Optional.empty();
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public List<Entity> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        var criteria =  session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);

        return session.createQuery(criteria).getResultList();
    }
}
