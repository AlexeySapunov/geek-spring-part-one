package ru.geekbrains.persist;

import java.math.BigDecimal;

public class Product {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
