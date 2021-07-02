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
        System.out.println("===================All Customers=====================");
        customerService.getAllCustomers(false).forEach(System.out::println);
        System.out.println("===================All Products======================");
        productService.getAllProducts(false).forEach(System.out::println);
        System.out.println("===================Create============================");
        Customer customer =  new Customer("Petrucco");
        Product product = new Product("Pumpkin", 4.25f);
        customerService.createCustomer(customer);
        productService.createProduct(product);
        customer = customerService.getCustomerByName(customer.getName(), true);
        product = productService.getProductByName("Pumpkin", false);
        System.out.println(customer);
        System.out.println(product);
        System.out.println("==================Order product(Update)==============");
        customerService.orderNewProduct(customer,product);
        customerService.getOrderedProducts(customer.getName()).forEach(System.out::println);
        System.out.println("==================Testing fix price=================");
        Float newCost = 100000f;
        System.out.println("Updating Cost on " + newCost);
        product.setCost(newCost);
        productService.updateProduct(product);
        System.out.println("Product from order");
        System.out.println(customerService.getOrderedPriceByCustomerAndProduct(customer, product));
        System.out.println("Product from base:");
        System.out.println(productService.getProductByName("Pumpkin", false));
        System.out.println("===================Delete============================");
        System.out.println("Deleting : " + customer);
        customerService.deleteCustomer(customer);
        System.out.println("Deleting : " + product);
        productService.deleteProduct(product);
        System.out.println("===================All Customers=====================");
        customerService.getAllCustomers(false).forEach(System.out::println);
        System.out.println("===================All Products======================");
        productService.getAllProducts(false).forEach(System.out::println);

    }
}
