package com.cart.dtos;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String about;
}
