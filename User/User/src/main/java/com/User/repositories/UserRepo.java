package com.User.repositories;

import com.User.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {

    User findUserByEmail(String email);
}
