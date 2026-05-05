package com.example.ecommerce_exam.repository;
import com.example.ecommerce_exam.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Long> {}