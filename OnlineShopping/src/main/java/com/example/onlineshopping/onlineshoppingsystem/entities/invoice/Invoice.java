package com.example.onlineshopping.onlineshoppingsystem.entities.invoice;

import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
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
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private Long invoiceId;

    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EnumInvoiceStatus status;

    @OneToMany(mappedBy = "invoice")
    List<InvoiceItem> items;

}