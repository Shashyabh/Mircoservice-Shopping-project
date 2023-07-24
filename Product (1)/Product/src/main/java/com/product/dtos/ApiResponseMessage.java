package com.product.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiResponseMessage {
    String message;
    HttpStatus status;
    boolean success;
}
