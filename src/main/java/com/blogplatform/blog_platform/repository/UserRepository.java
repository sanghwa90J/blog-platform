package com.blogplatform.blog_platform.repository;

import com.blogplatform.blog_platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<Object> findByUsername(String username);
}
