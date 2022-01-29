package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    public static Specification<Category> nameLike(String pattern) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + pattern + "%");
    }
}
