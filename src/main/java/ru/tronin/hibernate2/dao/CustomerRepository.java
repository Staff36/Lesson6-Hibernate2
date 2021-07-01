package ru.tronin.hibernate2.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository implements Idao<Customer, Long>{
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.save(customer);
            fixOrderedPrice(customer, session);
            session.getTransaction().commit();
        }
    }

    @Override
    public Customer getById(Long id) {
        Customer customer;
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            customer = session.get(Customer.class, id);
            session.getTransaction().commit();
        }
        return customer;
    }

    @Override
    public void update(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.update(customer);
            fixOrderedPrice(customer, session);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.delete(customer);
            session.getTransaction().commit();
        }
    }

    public List<Product> getOrderedProductsByCustomerId(Long id){
        try(Session session = sessionFactory.openSession()){
            List<Product> products;
            session.getTransaction().begin();
            Customer customer = session.get(Customer.class, id);
            Hibernate.initialize(customer.getProducts());
            products = customer.getProducts();
            session.getTransaction().commit();
            return products;
        }
    }

    private void fixOrderedPrice(Customer customer, Session session){
        if (customer.getProducts() == null){
            return;
        }
        customer.getProducts().forEach(product -> {
            NativeQuery query = session.createNativeQuery("update products_and_customers.customers_products set cost =:cost where products_id =:products_id and customers_id =:customers_id");
            query.setParameter("cost", product.getCost());
            query.setParameter("products_id", product.getId());
            query.setParameter("customers_id", customer.getId());
            query.executeUpdate();
        } );
    }

    public void initializeProductList(Customer customer){
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            Hibernate.initialize(customer.getProducts());
            session.getTransaction().commit();
        }
    }


    public Float getOrderedPrice(Customer customer, Product product) {
        if (product.getId() == null || customer.getId() == null){
            return null;
        }
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
                NativeQuery query = session.createNativeQuery("select u.cost from products_and_customers.customers_products u where customers_id =:customers_id and products_id =:products_id");
                query.setParameter("customers_id", customer.getId());
                query.setParameter("products_id", product.getId());
                Double doubleCost = (Double) query.getSingleResult();
                Float cost = Float.valueOf("" + doubleCost);
            session.getTransaction().commit();
            return cost;
        }
    }
}
