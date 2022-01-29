package ru.geekbrains.service.dto;

import ru.geekbrains.persist.Product;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    private List<Product> products;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
