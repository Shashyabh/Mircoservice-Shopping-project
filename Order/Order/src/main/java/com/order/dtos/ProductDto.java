package com.order.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int quantity;
    private Date addedDate;
}
