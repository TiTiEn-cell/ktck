package com.example.ecommerce_exam.repository;
import com.example.ecommerce_exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}