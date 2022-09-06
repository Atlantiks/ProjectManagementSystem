package ua.com.goit;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var x = ConnectionManager.openConnection();
        System.out.println(x.getTransactionIsolation());
    }

}
