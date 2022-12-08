package ua.com.goit.repository;

import org.hibernate.SessionFactory;
import ua.com.goit.entity.Skill;

public class SkillRepository extends BasicRepository<Integer, Skill> {

    public SkillRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Skill.class);
    }
}
