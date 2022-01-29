package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.CategorySpecification;
import ru.geekbrains.service.dto.CategoryDto;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort) {
        Specification<Category> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(CategorySpecification.nameLike(nameFilter.get()));
        }

        return categoryRepository.findAll(spec,
                        PageRequest.of(page, size, Sort.by(sort)))
                .map(CategoryServiceImpl::convertToDto);
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryServiceImpl::convertToDto);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = new Category(
                categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getProducts());
        Category saved = categoryRepository.save(category);
        return convertToDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    private static CategoryDto convertToDto(Category cat) {
        return new CategoryDto(
                cat.getId(),
                cat.getName(),
                cat.getProducts()
        );
    }
}
