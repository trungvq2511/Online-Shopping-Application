package com.example.onlineshopping.onlineshoppingsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String identityCard;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;
    private Boolean verified;
//    private List<CartItem> cartItem;
//    List<Role> role;
//    private List<Rating> ratings;
}
