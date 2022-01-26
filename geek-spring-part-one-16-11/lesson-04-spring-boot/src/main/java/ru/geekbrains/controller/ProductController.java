package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("nameFilter") Optional<String> nameFilter,
                           @RequestParam("minPriceFilter") Optional<BigDecimal> minPriceFilter,
                           @RequestParam("maxPriceFilter") Optional<BigDecimal> maxPriceFilter,
                           @RequestParam("minMaxPriceFilter") Optional<BigDecimal> minMaxPriceFilter) {
        logger.info("Product filter with name pattern {}", nameFilter.orElse(null));
        logger.info("Product filter with min price {}", minPriceFilter.orElse(null));
        logger.info("Product filter with max price {}", maxPriceFilter.orElse(null));
        logger.info("Product filter with min between max price {}", minMaxPriceFilter.orElse(null));

//        Specification<Product> spec = Specification.where(null);
//        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
//            spec.and(ProductSpecification.nameLike(nameFilter.get()));
//        } else if (minPriceFilter.isPresent()) {
//            spec.and(ProductSpecification.minPriceFilter(minPriceFilter.get()));
//        } else if (maxPriceFilter.isPresent()) {
//            spec.and(ProductSpecification.maxPriceFilter(maxPriceFilter.get()));
//        } else
//            minMaxPriceFilter.ifPresent(bigDecimal -> spec.and(ProductSpecification.minMaxPriceFilter(bigDecimal, bigDecimal)));

        List<Product> products;

        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            products = productRepository.findProductByNameLike("%" + nameFilter.get() + "%");
        } else if (minPriceFilter.isPresent() || maxPriceFilter.isPresent() || minMaxPriceFilter.isPresent() || nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            products = productRepository.findByFilter("%" + nameFilter.get() + "%", minPriceFilter.get(), maxPriceFilter.get());
        } else {
            products = productRepository.findAll();
        }

//        model.addAttribute("products", productRepository.findAll(spec));
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        return "product_form";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping
    public String save(@Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "product_form";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{idDel}")
    public String delete(@PathVariable("idDel") Long idDel, @Valid Product product) {
        if (product != null) {
            productRepository.deleteById(idDel);
        }
        return "product_form";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}
