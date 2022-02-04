package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.service.dto.ProductDto;

import java.util.Optional;

public interface CartService {

    Page<ProductDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort);

    void addProduct(Long id);

    void delProduct(long id);
}
