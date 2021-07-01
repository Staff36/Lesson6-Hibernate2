package ru.tronin.hibernate2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float cost;
    @ManyToMany(mappedBy = "products")
    private List<Customer> customers;

    public Product(String name, Float cost) {
        this.name = name;
        this.cost = cost;
    }

    public Product(String name, Float cost, List<Customer> customers) {
        this.name = name;
        this.cost = cost;
        this.customers = customers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost);
        if(this.customers != null){
            sb.append("Products[ ");
            customers.forEach(customer -> {
                sb.append("Product{id=" + customer.getId() +
                        ", name='" + customer.getName() + "\'}" );
            });
            sb.append(']');
        }
        sb.append('}');
        return  sb.toString();
    }
}

