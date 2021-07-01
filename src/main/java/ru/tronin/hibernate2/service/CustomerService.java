package ru.tronin.hibernate2.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tronin.hibernate2.dao.CustomerRepository;
import ru.tronin.hibernate2.model.Customer;
import ru.tronin.hibernate2.model.Product;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public List<Product> getProductListByCustomerId(Long id){
        Customer customer = customerRepository.getById(id);
        Hibernate.initialize(customer.getProducts());
        return customer.getProducts();
    }

}
