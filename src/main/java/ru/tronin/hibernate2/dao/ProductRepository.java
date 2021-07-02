package ru.tronin.hibernate2.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tronin.hibernate2.model.Product;

import java.util.List;

@Repository
public class ProductRepository implements Idao<Product, Long, String>{
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
    public Product getById(Long id, boolean initializeCustomers) {
        Product product;
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            product = session.get(Product.class, id);
            if(initializeCustomers){
                Hibernate.initialize(product.getCustomers());
            }
            session.getTransaction().commit();
        }
        return product;
    }

    @Override
    public Product getByName(String name, boolean initializeCustomers){
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            Query query = session.createQuery("from Product where name =:name");
            query.setParameter("name", name);
            Product product = (Product) query.getSingleResult();
            if(initializeCustomers){
                Hibernate.initialize(product.getCustomers());
            }
            session.getTransaction().commit();
            return product;
        }
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
            session.createNativeQuery("delete from products_and_customers.customers_products " +
                    "where products_id = :products_id")
                    .setParameter("products_id", product.getId())
                    .executeUpdate();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Product> getAll(boolean initializeCustomers) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            List<Product> products = session.createQuery("from Product").getResultList();
            if (initializeCustomers) {
                products.forEach(product -> Hibernate.initialize(product.getCustomers()));
            }
            session.getTransaction().commit();
            return products;
        }
    }

}
