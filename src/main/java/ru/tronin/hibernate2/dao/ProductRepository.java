package ru.tronin.hibernate2.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;

import java.util.Collections;
import java.util.List;

@Repository
public class ProductRepository implements Idao<Product, Long>{
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(Product product) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public Product getById(Long id) {
        Product product;
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            product = session.get(Product.class, id);
            session.getTransaction().commit();
        }
        return product;
    }

    @Override
    public void update(Product product) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.update(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Product product) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    public void initializeCustomersList(Product product){
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            Hibernate.initialize(product.getCustomers());
            session.getTransaction().commit();
        }
    }
}
