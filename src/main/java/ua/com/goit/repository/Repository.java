package ua.com.goit.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<Key, Entity> {
    Entity save(Entity e);

    void delete(Key id);

    void update(Entity e);

    Optional<Entity> findById(Key id);

    List<Entity> findAll();
}
