package ru.tronin.hibernate2.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.hibernate2.dao.CustomerRepository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Data
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Product> getOrderedProducts(String name){
        Customer customer = customerRepository.getByName(name, true);
        customer.getProducts().forEach(product -> getOrderedPriceByCustomerAndProduct(customer,product));
        return customer.getProducts();
    }

    public Product getOrderedPriceByCustomerAndProduct(Customer customer, Product product){
        if (customer.getId() == null ||
                product.getId() == null ||
                customer.getProducts() == null){
            return null;
        }
        Float orderedCost = customerRepository.getOrderedCost(customer, product);
        product.setCost(orderedCost);
        return product;
    }

    public void orderNewProduct(Customer customer, Product product){
        customer = customerRepository.getByName(customer.getName(), true);
        Set<Product> productSet = new HashSet<>(customer.getProducts());
        productSet.add(product);
        customer.setProducts(productSet.stream().toList());
        customerRepository.update(customer);
    }

    public void createCustomer(Customer customer){
        customerRepository.create(customer);
    }

    public Customer getCustomerById(Long id,boolean initializeProducts){
        return customerRepository.getById(id, initializeProducts);
    }

    public void updateCustomer(Customer customer){
        customerRepository.update(customer);
    }

    public void deleteCustomer(Customer customer){
        customerRepository.delete(customer);
    }

    public List<Customer> getAllCustomers(boolean initializeCustomers){
        return customerRepository.getAll(initializeCustomers);
    }

    public Customer getCustomerByName(String name, boolean initializeProducts) {
        return customerRepository.getByName(name, initializeProducts);
    }
}
