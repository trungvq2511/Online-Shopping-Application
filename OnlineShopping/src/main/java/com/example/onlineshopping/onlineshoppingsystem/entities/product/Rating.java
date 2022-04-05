package com.example.onlineshopping.onlineshoppingsystem.entities.product;

import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "rating")
public class Rating {
    @EmbeddedId
    private UserProductKey key;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "comment")
    private String comment;

    @Embeddable
    public static class UserProductKey implements Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "product_id")
        private Long productId;

    }
}