package com.example.onlineshopping.onlineshoppingsystem.entities.invoice;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
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
@Table(name = "invoice_item")
public class InvoiceItem {
    @EmbeddedId
    private InvoiceItemKey invoiceItemKey;

    @ManyToOne
    @JoinColumn(name = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private Product item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "money", nullable = false)
    private Double price;

    @Column(name = "totalInCart", nullable = false)
    private Double totalInCart;

    public InvoiceItem(Invoice invoice, Product item, Integer quantity, Double price, Double totalInCart) {
        this.invoiceItemKey = new InvoiceItemKey(invoice.getInvoiceId(), item.getProductId());
        this.invoice = invoice;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.totalInCart = totalInCart;
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItemKey implements Serializable {
        @Column(name = "invoice_id")
        private Long invoiceId;

        @Column(name = "product_id")
        private Long productId;
    }
}