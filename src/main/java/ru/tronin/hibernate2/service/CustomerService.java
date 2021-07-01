package ru.tronin.hibernate2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.hibernate2.dao.CustomerRepository;
import ru.tronin.hibernate2.dao.ProductRepository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;


    public List<Product> getOrderedProducts(Long id){
        Customer customer = customerRepository.getById(id);
        customerRepository.initializeProductList(customer);
        customer.getProducts().forEach(product -> getOrderedPriceByCustomerAndProduct(customer,product));
        return customer.getProducts();
    }

    public Product getOrderedPriceByCustomerAndProduct(Customer customer, Product product){
        if (customer.getId() == null ||
                product.getId() == null ||
                customer.getProducts() == null){
            return null;
        }
        Float orderedCost = customerRepository.getOrderedPrice(customer, product);
        product.setCost(orderedCost);
        return product;
    }

    public List<Customer> getCustomersByProductId(Long id){
        Product product = productRepository.getById(id);
        productRepository.initializeCustomersList(product);
        return product.getCustomers();
    }

}
