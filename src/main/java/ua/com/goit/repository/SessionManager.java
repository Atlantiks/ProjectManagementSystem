package ua.com.goit.repository;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import ua.com.goit.entity.*;

@UtilityClass
public class SessionManager {
    private SessionFactory sf = null;

    public static SessionFactory buildSessionFactory() {
        if (sf == null) {
            Configuration configuration = buildConfiguration();
            configuration.configure();
            sf = configuration.buildSessionFactory();
        }

        return sf;
    }


    public static Configuration buildConfiguration() {
        Configuration config = new Configuration();

        config.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        config.addAnnotatedClass(Developer.class);
        config.addAnnotatedClass(Company.class);
        config.addAnnotatedClass(Project.class);
        config.addAnnotatedClass(Customer.class);
        config.addAnnotatedClass(Skill.class);

        return config;
    }
}
