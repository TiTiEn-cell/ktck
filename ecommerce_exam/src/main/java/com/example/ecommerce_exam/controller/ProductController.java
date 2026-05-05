package com.example.ecommerce_exam.controller;
import com.example.ecommerce_exam.entity.Product;
import com.example.ecommerce_exam.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductRepository productRepo;

    @GetMapping
    public String listUserProducts(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Product> products;

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productRepo.findByNameContainingIgnoreCase(keyword.trim());
        } else {
            products = productRepo.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "product-list";
    }
}