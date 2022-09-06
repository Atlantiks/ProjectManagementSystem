package ua.com.goit;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        String SQL = "CREATE TABLE IF NOT EXISTS developers_test (" +
                "id SERIAL," +
                "first_name VARCHAR(255)," +
                "last_name VARCHAR(255)" +
                ");";
        try (Connection connection = ConnectionManager.openConnection();
             Statement statement = connection.createStatement()) {
            var x = statement.execute(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
