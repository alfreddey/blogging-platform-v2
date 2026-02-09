package com.example.demo.mapper;

public interface Mapper<T, R, S> {
    R toResponse(T entity);
    T toEntity(S request);
}
