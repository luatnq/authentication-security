package com.example.security.repository;


import com.example.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User save(User user);

    boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    User findUserByEmail(String email);

//    User findUserByUsernameOrEmail(String username, String email);

    User findUserByUsername(String username);

    User findUserByEmailOrEmail(String emailUpper, String emailLower);
}
