package com.example.onlineshopping.onlineshoppingsystem.entities.user;

import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "identity_card", length = 12)
    private String identityCard;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column
    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> role;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;
}