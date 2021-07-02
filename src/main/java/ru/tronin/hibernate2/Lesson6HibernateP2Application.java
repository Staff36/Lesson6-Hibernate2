package ru.tronin.hibernate2;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.tronin.hibernate2.dao.CustomerRepository;
import ru.tronin.hibernate2.dao.ProductRepository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;
import ru.tronin.hibernate2.service.CustomerService;
import ru.tronin.hibernate2.service.ProductService;

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
    CustomerService customerService;

    @Autowired
    ProductService productService;


    @Override
    public void run(String... args) throws Exception {
        customerService.getAllCustomers().forEach(System.out::println);
        System.out.println("=====================================================");
        productService.getAllProducts().forEach(System.out::println);
        System.out.println("=====================================================");
        Customer customer =  new Customer("Petrucco");
        customerService.createCustomer(customer);
        Product product = new Product("Pumpkin", 4.25f);
        productService.createProduct(product);
        customer = customerService.getCustomerByName(customer.getName(), true);
        product = productService.getProductById(1L, false);
        customerService.orderNewProduct(customer,product);
        customerService.getOrderedProducts(customer.getName()).forEach(System.out::println);

    }
}
