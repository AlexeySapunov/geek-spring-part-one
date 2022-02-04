package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;
import ru.geekbrains.service.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final ProductRepository productRepository;

    private final List<Product> productList = new ArrayList<>();

    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort) {
        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                .map(CartServiceImpl::convertToDto);
    }


    @Override
    public void addProduct(Long id) {
        productList.add(productRepository.findById(id).orElse(null));
    }

    @Override
    public void delProduct(long id) {
        if (!productList.isEmpty()) {
            productList.removeIf(product -> product.getId().equals(id));
        }
    }

    private static ProductDto convertToDto(Product prod) {
        return new ProductDto(prod.getId(),
                              prod.getName(),
                              prod.getPrice(),
                              prod.getDescription(),
                              prod.getCategory()!=null ? prod.getCategory().getId():null,
                              prod.getCategory()!=null ? prod.getCategory().getName():null);
    }
}
