package com.cart.dtos;

import com.cart.entities.Cart;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private int quantity;
    private int totalPrice;
    private String productId;
    private String title;
    private String description;
}
