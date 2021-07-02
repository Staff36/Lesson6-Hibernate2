package ru.tronin.hibernate2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customers_products",
            joinColumns = @JoinColumn(name = "customers_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;


    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Customer{" +
                "id=" + id +
                ", name='" + name + '\'');
//        if(this.products != null){
//            sb.append("Products[ ");
//            products.forEach(product -> {
//                sb.append("Product{id=" + product.getId() +
//                        ", name='" + product.getName() + '\'' +
//                        ", cost='" + product.getCost() + "\'}");
//            });
//            sb.append(']');
//        }
            sb.append('}');


        return  sb.toString();
    }
}
