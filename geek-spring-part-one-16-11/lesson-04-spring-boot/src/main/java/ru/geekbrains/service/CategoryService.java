package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.service.dto.CategoryDto;

import java.util.Optional;

public interface CategoryService {

    Page<CategoryDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort);

    Optional<CategoryDto> findById(Long id);

    CategoryDto save(CategoryDto category);

    void deleteById(Long id);
}
