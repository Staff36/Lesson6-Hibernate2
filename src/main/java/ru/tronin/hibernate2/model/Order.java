package ru.tronin.hibernate2.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Customer customer;
    @ManyToMany
    List<Product> orderedProducts;
}
