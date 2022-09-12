package ua.com.goit.dao;

import ua.com.goit.entity.Skill;

import java.util.List;
import java.util.Optional;

public class SkillDao implements DataAccess<Integer, Skill> {

    @Override
    public Optional<Skill> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Skill save(Skill skill) {
        return null;
    }

    @Override
    public List<Skill> findAll() {
        return null;
    }

    @Override
    public boolean remove(Skill skill) {
        return false;
    }

    @Override
    public boolean removeById(Integer integer) {
        return false;
    }

    @Override
    public boolean update(Skill skill) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    enum SQL {
        INSERT("INSERT INTO skills (name, level) " +
                "VALUES (?,?)"),

        SELECT_ALL ("SELECT id, name, level " +
                "FROM skills " +
                "ORDER BY id"),

        SELECT_BY_ID("SELECT id, name, level FROM skills " +
                "WHERE id = ?"),

        UPDATE("UPDATE skills " +
                "SET name = ?, level = ? " +
                "WHERE id = ?"),

        DELETE_BY_ID("DELETE FROM skills WHERE id = ?"),

        DELETE_BY_NAME("DELETE FROM skills WHERE name = ? AND level = ?"),

        COUNT("SELECT count(id) FROM skills");

        SQL(String command) {
            this.command = command;
        }

        final String command;
    }
}
