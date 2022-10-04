package ua.com.goit.exception;

public class DeveloperNotFound extends RuntimeException {
    public DeveloperNotFound(String message) {
        super(message);
    }
}
