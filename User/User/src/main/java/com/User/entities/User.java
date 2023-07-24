package com.User.entities;

import com.User.dto.ProductDto;
import com.User.dto.RoleDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="users")
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    private String name;

    private String email;

    private String password;

    private String gender;

    private String about;

    @Transient
    private List<ProductDto> productlist=new ArrayList<>();

    @Transient
    private Set<RoleDto> roles=new HashSet<>();
}
