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
//@Table(name = "email_otp")
//public class EmailOTP {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "otp_id", nullable = false)
//    private Long otpId;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
//    private User user;
//
//    @Column(name = "otp_code", length = 6, nullable = false)
//    private String otpCode;
//
//    @Column(name = "expired_time", nullable = false)
//    private LocalDateTime expiredTime;
//}