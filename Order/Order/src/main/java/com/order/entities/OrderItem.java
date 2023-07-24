package com.order.entities;

import com.order.dtos.ProductDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;

    private int price;

    private String productId;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
