package com.order.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private String cartId;
    private String userId;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
