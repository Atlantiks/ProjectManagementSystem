package ua.com.goit.mapper;

public interface Mapper<F, T> {
    T mapFrom(F object);
    F mapTo(T object);
}
