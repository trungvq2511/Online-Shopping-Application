package com.example.onlineshopping.onlineshoppingsystem.entities.file;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileId;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "upload_time", nullable = false)
    private LocalDateTime uploadTime;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}