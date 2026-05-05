package com.example.ecommerce_exam.controller;
import com.example.ecommerce_exam.entity.Product;
import com.example.ecommerce_exam.repository.CategoryRepository;
import com.example.ecommerce_exam.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired private ProductRepository productRepo;
    @Autowired private CategoryRepository categoryRepo;

    @GetMapping
    public String list(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Product> products;

        // Nếu người dùng có nhập từ khóa tìm kiếm
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productRepo.findByNameContainingIgnoreCase(keyword.trim());
        } else {
            // Nếu không nhập gì, hiển thị tất cả
            products = productRepo.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword); // Giữ lại từ khóa trên ô tìm kiếm
        return "admin/product-list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepo.findAll());
        return "admin/product-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepo.findAll());
        return "admin/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAll());
            return "admin/product-form";
        }
        productRepo.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/admin/products";
    }
}