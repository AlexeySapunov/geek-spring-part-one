package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.annotation.PostConstruct;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void init() {
        productRepository.save(new Product(1L, "Product 1", 0.0));
        productRepository.save(new Product(2L, "Product 2", 0.0));
        productRepository.save(new Product(3L, "Product 3", 0.0));
        productRepository.save(new Product(4L, "Product 4", 0.0));
        productRepository.save(new Product(5L, "Product 5", 0.0));
    }

    public long count() {
        return productRepository.findAll().size();
    }

    public String nameProduct(long id) {
        return productRepository.findById(id).getName();
    }
}
