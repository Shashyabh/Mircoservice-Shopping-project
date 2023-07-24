package com.order.services.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.dtos.*;
import com.order.entities.Order;
import com.order.entities.OrderItem;
import com.order.repositories.OrderItemRepo;
import com.order.repositories.OrderRepo;
import com.order.services.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    private Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        String userId = request.getUserId();
        String cartId = request.getCartId();
        UserDto user = restTemplate.getForObject("http://localhost:6000/users/getSingleUser/" + userId, UserDto.class);
        CartDto cart = restTemplate.getForObject("http://localhost:6002/carts/" + cartId, CartDto.class);

        List<CartItemDto> cartItem = cart.getCartItem();
        logger.info("->>>>>`"+cartItem.toString());

        Order order = Order.builder()
                .billingName(request.getBillingName())
                .billingPhone(request.getBillingPhone())
                .billingAddress(request.getBillingAddress())
                .orderedDate(new Date())
                .orderId(UUID.randomUUID().toString())
                .userId(user.getUserId())
                .build();

        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItem.stream().map(items -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(items.getQuantity())
                    .productId(items.getProductId())
                    .description(items.getDescription())
                    .title(items.getTitle())
                    .price(items.getQuantity() * items.getTotalPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get()+orderItem.getPrice());
            return orderItem;
        }).collect(Collectors.toList());

//        cartItem.stream().map(orderItem -> {
//            logger.info("---->>"+orderItem.getTitle());
//            logger.info("---->>"+orderItem.getTitle()+"");
//            logger.info("---->>"+orderItem.getProductId()+"");
//            logger.info("---->>"+orderItem.getDescription()+"");
//
//            return orderItem;
//        });

        order.setOrderAmount(orderAmount.get());
        order.setOrderItems(orderItems);

//        cart.getCartItem().clear();
//        CartDto cartDto = modelMapper.map(cart, CartDto.class);
//        restTemplate.postForEntity("http://localhost:6002/carts/save",cartDto,CartDto.class);
        //cartRepo.save(cart);
        Order savedOrder = orderRepo.save(order);
        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrderOfUser(String userId) {
        UserDto user = restTemplate.getForObject("http://localhost:6000/users/getSingleUser/" + userId, UserDto.class);
        List<Order> orders = orderRepo.findByUserId(user.getUserId());

        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }
}
