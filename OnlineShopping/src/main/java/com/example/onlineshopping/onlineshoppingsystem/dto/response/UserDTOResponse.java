package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDTOResponse {
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
    List<RoleDTOResponse> role;
}
