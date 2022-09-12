package ua.com.goit.dao;

import java.util.List;
import java.util.Optional;

public interface DataAccess<Key, Entity> {
    Optional<Entity> findById(Key key);
    Entity save(Entity entity);
    List<Entity> findAll();
    boolean remove(Entity entity);
    boolean removeById(Key key);
    boolean update(Entity entity);

    int count();

}
