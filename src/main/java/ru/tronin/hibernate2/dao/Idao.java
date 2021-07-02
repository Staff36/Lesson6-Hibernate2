package ru.tronin.hibernate2.dao;

import java.util.List;

public interface Idao <E,I,S>{
    void create(E e);
    E getById(I id, boolean initialize);
    void update(E e);
    void delete(E e);
    List<E> getAll();
    E getByName(S name, boolean initialize);

}
