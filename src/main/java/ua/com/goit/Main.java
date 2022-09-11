package ua.com.goit;

import ua.com.goit.dao.ConnectionManager;
import ua.com.goit.dao.DataAccess;
import ua.com.goit.dao.DeveloperDao;
import ua.com.goit.entity.Developer;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        DataAccess<Integer, Developer> devDao = new DeveloperDao(connectionManager.getConnection());

        devDao.remove(new Developer(30,"Sergii","Shynkarenko","M",null,null));

        var x = devDao.findAll();
        x.stream().forEach(System.out::println);

/*        Developer dev = new Developer("Sergii","Shynkarenko","M");
        devDao.save(dev);*/
/*
        var y = devDao.findAll();
        y.stream().forEach(System.out::println);*/


    }

}
