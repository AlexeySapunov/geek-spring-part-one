package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    @Autowired
    private ProductRepository productRepository;

    private final List<Product> shoppingList = new ArrayList<>();

    public void addProduct(long id) {
        shoppingList.add(productRepository.findById(id));
    }

    public void delProduct(long id) {
        if (!shoppingList.isEmpty()) {
            shoppingList.remove(productRepository.findById(id));
        } else {
            throw new IllegalArgumentException("Cart is empty! Please, add product");
        }
    }

    public long count() {
        return shoppingList.size();
    }
}
