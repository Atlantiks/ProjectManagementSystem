package ua.com.goit;

import ua.com.goit.dao.*;
import ua.com.goit.entity.Developer;
import ua.com.goit.entity.Project;

import java.util.List;
import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DataAccess<Integer, Developer> devDao = new DeveloperDao(connectionManager.getConnection());
        DataAccess<Integer, Project> projectDao = new ProjectDao(connectionManager.getConnection());

        projectDao.removeById(13);

        var all = projectDao.findAll();
        all.forEach(System.out::println);

/*        var all = projectDao.findAll();
        all.forEach(System.out::println);

        var byId = projectDao.findById(12);
        var tba = byId.orElseGet(() -> new Project("TBA"));
        tba.setDescription("TBA");
        tba.setStatus("Inactive");

        projectDao.save(tba);

        System.out.println(projectDao.count());

        all = projectDao.findAll();
        all.forEach(System.out::println);*/


    }

}
