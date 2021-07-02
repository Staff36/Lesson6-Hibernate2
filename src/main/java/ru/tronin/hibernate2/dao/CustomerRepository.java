package ru.tronin.hibernate2.dao;

import lombok.Data;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;
import java.util.List;

@Repository
@Data
public class CustomerRepository implements Idao<Customer, Long, String>{
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.save(customer);
            session.getTransaction().commit();
        }
        fixOrderedPrice(customer);
    }

    @Override
    public List<Customer> getAll(boolean initializeProducts) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            List<Customer> customers = session.createQuery("from Customer").getResultList();
            if (initializeProducts){
            customers.forEach(customer -> Hibernate.initialize(customer.getProducts()));
            }
            session.getTransaction().commit();
            return customers;
        }
    }

    @Override
    public Customer getById(Long id, boolean initializeProducts) {
        Customer customer;
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            customer = session.get(Customer.class, id);
            if(initializeProducts){
                Hibernate.initialize(customer.getProducts());
            }
            session.getTransaction().commit();
        }
        return customer;
    }

    @Override
    public Customer getByName(String name, boolean initializeProducts){
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            Query query = session.createQuery("from Customer where name =:name");
            query.setParameter("name", name);
            Customer customer = (Customer) query.getSingleResult();
            if(initializeProducts){
                Hibernate.initialize(customer.getProducts());
            }
            session.getTransaction().commit();
            return customer;
        }
    }

    @Override
    public void update(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.update(customer);
            session.getTransaction().commit();
        }
        fixOrderedPrice(customer);
    }

    @Override
    public void delete(Customer customer) {
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            session.createNativeQuery("delete from products_and_customers.customers_products " +
                    "where customers_id = :customers_id")
                    .setParameter("customers_id", customer.getId()).executeUpdate();
            session.delete(customer);
            session.getTransaction().commit();
        }
    }

    private void fixOrderedPrice(Customer customer){
        if (customer.getProducts() == null){
            return;
        }
        try (Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
            NativeQuery query;
            for (Product product : customer.getProducts()) {
                 session.createNativeQuery("UPDATE products_and_customers.customers_products " +
                         "SET cost = :cost " +
                         "WHERE products_id = :products_id " +
                         "AND customers_id = :customers_id")
                        .setParameter("cost", product.getCost())
                        .setParameter("products_id", product.getId())
                        .setParameter("customers_id", customer.getId())
                        .executeUpdate();
            }
            session.getTransaction().commit();
        }
    }


    public Float getOrderedCost(Customer customer, Product product) {
        if (product.getId() == null || customer.getId() == null){
            return null;
        }
        try(Session session = sessionFactory.openSession()){
            session.getTransaction().begin();
                NativeQuery query = session.createNativeQuery("select u.cost " +
                        "from products_and_customers.customers_products u " +
                        "where customers_id = :customers_id " +
                        "and products_id = :products_id");
                query.setParameter("customers_id", customer.getId());
                query.setParameter("products_id", product.getId());
                Double doubleCost = (Double) query.getSingleResult();
                Float cost = Float.valueOf("" + doubleCost);
            session.getTransaction().commit();
            return cost;
        }
    }
}
