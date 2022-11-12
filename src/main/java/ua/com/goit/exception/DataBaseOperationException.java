package ua.com.goit.exception;

public class DataBaseOperationException extends RuntimeException {

    public DataBaseOperationException(String message) {
        super(message);
    }
}
