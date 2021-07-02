package ru.tronin.hibernate2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.hibernate2.dao.ProductRepository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Customer> getCustomersByProductId(Long id){
        Product product = productRepository.getById(id, true);
        return product.getCustomers();
    }

    public void createProduct(Product product){
        productRepository.create(product);
    }

    public Product getProductById(Long id, boolean initializeCustomers){
        return productRepository.getById(id, initializeCustomers);
    }

    public Product getProductByName(String name, boolean initializeCustomers){
        return productRepository.getByName(name, initializeCustomers);
    }

    public void updateProduct(Product product){
        productRepository.update(product);
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }

    public List<Product> getAllProducts(boolean initializeProducts){
        return productRepository.getAll(initializeProducts);
    }
}
