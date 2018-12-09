package model.repository;

import java.util.List;

public interface Repository <T> {

    void add(T obj);
    List<T> getAll();
    void delete(String ID);
    void update(String ID, T obj);
}
