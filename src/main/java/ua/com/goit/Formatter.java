package ua.com.goit;

public class Formatter {
    public static String capitalize(String input) {
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
