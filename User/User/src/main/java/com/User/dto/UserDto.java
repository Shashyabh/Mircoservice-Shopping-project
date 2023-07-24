package com.User.dto;

import lombok.*;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<ProductDto> productlist=new ArrayList<>();
    private Set<RoleDto> roles=new HashSet<>();
}
