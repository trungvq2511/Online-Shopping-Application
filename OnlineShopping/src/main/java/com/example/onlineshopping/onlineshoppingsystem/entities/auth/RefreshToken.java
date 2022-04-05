//package com.example.onlineshopping.onlineshoppingsystem.entities.auth;
//
//import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//
//
//@Entity
//@Table(name = "refresh_token")
//public class RefreshToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "token_id", nullable = false)
//    private Long tokenID;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
//    private User user;
//
//    @Column(name = "token", nullable = false, unique = true)
//    private String token;
//
//    @Column(name = "expired_date", nullable = false)
//    private LocalDateTime expiredDate;
//}