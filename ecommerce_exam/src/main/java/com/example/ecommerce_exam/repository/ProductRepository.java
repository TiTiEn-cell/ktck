package com.example.ecommerce_exam.repository;

import com.example.ecommerce_exam.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Hàm này rất quan trọng: Spring sẽ tự dịch tên hàm này thành câu lệnh SQL tìm kiếm LIKE %keyword%
    List<Product> findByNameContainingIgnoreCase(String keyword);
}