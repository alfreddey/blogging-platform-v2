package com.example.demo.cache;

import java.util.List;

public interface Cache<T> {
    void put(T item);
    void put(String id, T item);
    T getById(String id);
    List<T> getAll();
    void remove(String id);
}
