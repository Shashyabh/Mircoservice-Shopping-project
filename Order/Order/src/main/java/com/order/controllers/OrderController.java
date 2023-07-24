package com.order.controllers;

import com.order.dtos.CreateOrderRequest;
import com.order.dtos.OrderDto;
import com.order.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest orderRequest){
        OrderDto order = this.orderService.createOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<OrderDto>> getAllOrdersOfUser(@PathVariable String userId){
        List<OrderDto> orderOfUser = this.orderService.getOrderOfUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }

}
