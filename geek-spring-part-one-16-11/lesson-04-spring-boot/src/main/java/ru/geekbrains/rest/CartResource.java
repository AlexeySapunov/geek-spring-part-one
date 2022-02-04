package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.dto.ProductDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public Page<ProductDto> search(@RequestParam("nameFilter") Optional<String> nameFilter,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size,
                                   @RequestParam("sort") Optional<String> sort){
        return cartService.findAll(
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s -> !s.isBlank()).orElse("id"));
    }


    @PostMapping("sub/{id}")
    public void add(@PathVariable("id") Long id) {
        cartService.addProduct(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        cartService.delProduct(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExceptionHandler(NotFoundException ex) {
        return  new ErrorDto(ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgumentException(IllegalArgumentException ex) {
        return  new ErrorDto(ex.getMessage());
    }
}
