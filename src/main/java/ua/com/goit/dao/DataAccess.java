package ua.com.goit.dao;

import java.util.List;

public interface DataAccess<Key, Entity> {
    Entity getById(Key key);
    Entity save(Entity entity);
    List<Entity> get();
    boolean remove(Entity entity);
    boolean removeById(Key key);
    boolean update(Entity entity);

}
