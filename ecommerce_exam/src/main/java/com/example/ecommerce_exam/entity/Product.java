package com.example.ecommerce_exam.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên không được rỗng")
    private String name;

    @NotNull(message = "Giá không được rỗng")
    @Min(value = 1, message = "Giá lớn hơn 0")
    private Double price;

    @NotNull(message = "Số lượng không được rỗng")
    @Min(value = 0, message = "Số lượng lớn hơn hoặc bằng 0")
    private Integer quantity;

    private String description;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Bắt buộc chọn danh mục")
    private Category category;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage_url() { return image_url; }
    public void setImage_url(String image_url) { this.image_url = image_url; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}