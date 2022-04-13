package com.example.onlineshopping.onlineshoppingsystem.entities.cart;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
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
@Table(name = "cart_item")
public class CartItem implements Serializable {
    @EmbeddedId
    private CartItemKey cartItemKey;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "totalInCart", nullable = false)
    private double totalInCart;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class CartItemKey implements Serializable {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "product_id")
        private Long productId;
    }

    public CartItem(User user, Product product, Integer quantity) {
        this.cartItemKey = new CartItemKey(user.getUserId(), product.getProductId());
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
}