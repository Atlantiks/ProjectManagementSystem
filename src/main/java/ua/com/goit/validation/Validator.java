package ua.com.goit.validation;

public interface Validator<T> {

    boolean isValid(T object);
}
