package com.example.onlineshopping.onlineshoppingsystem.entities.product;

import com.example.onlineshopping.onlineshoppingsystem.entities.file.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "rating_score")
    private Double ratingScore;

    @Column(name = "manufactured_factory")
    private String manufacturedFactory;

    @Column(name = "made_in")
    private String madeIn;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "product")
    private List<File> files;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", ratingScore=" + ratingScore +
                ", manufacturedFactory='" + manufacturedFactory + '\'' +
                ", madeIn='" + madeIn + '\'' +
                '}';
    }
}