package ua.com.goit;

import ua.com.goit.dao.ConnectionManager;
import ua.com.goit.entity.Developer;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        Developer dev = new Developer("Sergei","Petrov");
        System.out.println(dev.hashCode());

        String SQL = "SELECT first_name, salary " +
                "FROM developers";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setFetchSize(100);

            var x = statement.executeQuery();

            while (x.next()) {
                System.out.println(x.getString(1) + " " + x.getInt(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

}
