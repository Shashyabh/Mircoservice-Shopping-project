package com.order.services;

import com.order.dtos.CreateOrderRequest;
import com.order.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    //Create order
    OrderDto createOrder(CreateOrderRequest request);

    //Remove order

    void removeOrder(String orderId);

    //Get order of User

    List<OrderDto> getOrderOfUser(String userId);
}
