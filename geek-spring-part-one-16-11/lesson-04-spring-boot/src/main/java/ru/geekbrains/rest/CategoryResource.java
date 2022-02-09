package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.dto.CategoryDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product/category")
public class CategoryResource {

    private final CategoryService categoryService;

    @Autowired
    public CategoryResource(CategoryService categoryService) {this.categoryService = categoryService;}


    @GetMapping
    public Page<CategoryDto> search(@RequestParam("nameFilter") Optional<String> nameFilter,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sort") Optional<String> sort) {
        return categoryService.findAll(
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s -> !s.isBlank()).orElse("id"));

    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/{id}")
    public CategoryDto findOne(@PathVariable("id") Long id) {
        return categoryService.findById(id).orElseThrow(() -> new NotFoundException("Category with id " + id + " no found"));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        if (categoryDto.getId() != null) {
            throw new IllegalArgumentException("For category creating id have to be null");
        }
        return categoryService.save(categoryDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        if (categoryDto.getId() == null) {
            throw new IllegalArgumentException("For category update id have to be not null");
        }
        return categoryService.save(categoryDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
        return new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new ErrorDto(ex.getMessage());
    }
}
