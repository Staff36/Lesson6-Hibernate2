package ru.tronin.hibernate2;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.tronin.hibernate2.dao.CustomerRepository;
import ru.tronin.hibernate2.dao.ProductRepository;
import ru.tronin.hibernate2.model.Product;
import ru.tronin.hibernate2.service.CustomerService;

import java.util.List;

@SpringBootApplication
public class Lesson6HibernateP2Application implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(Lesson6HibernateP2Application.class, args);
    }

    @Bean
    public SessionFactory getSessionFactory(){
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration()
                .configure()
                .buildSessionFactory();
        return sessionFactory;
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(customerRepository.getById(1L));
        System.out.println("=====================================================");
        customerRepository.getOrderedProductsByCustomerId(2L).forEach(System.out::println);
        System.out.println("=====================================================");
        productRepository.getCustomersOfProductByProductId(1L).forEach(System.out::println);
        System.out.println("=====================================================");
        customerRepository.getOrderedProductsWithFixedPricesByCustomerId(3L).forEach(System.out::println);
    }
}
