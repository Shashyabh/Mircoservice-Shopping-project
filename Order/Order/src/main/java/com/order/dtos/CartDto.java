package com.order.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date date;
    private UserDto user;
    private List<CartItemDto> cartItem=new ArrayList<>();
}
