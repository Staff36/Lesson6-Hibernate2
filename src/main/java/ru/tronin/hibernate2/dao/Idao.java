package ru.tronin.hibernate2.dao;

public interface Idao <E,I>{
    void create(E e);
    E getById(I id);
    void update(E e);
    void delete(E e);

}
