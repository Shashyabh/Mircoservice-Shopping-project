package com.product.dtos;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int quantity;
    private Date addedDate;
    private CategoryDto category;
}
