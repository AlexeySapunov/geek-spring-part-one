package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.UserService;
import ru.geekbrains.service.dto.UserDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDto> search(@RequestParam("nameFilter") Optional<String> nameFilter,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("sort") Optional<String> sort){
        return userService.findAll(
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s -> !s.isBlank()).orElse("id"));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/{id}")
    public  UserDto findOne(@PathVariable("id") Long id){
        return userService.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id " + id + "not found"));

    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto){
        if(userDto.getId() != null){
            throw  new IllegalArgumentException("For user creation id have to be null");
        }
        return userService.save(userDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping
    public UserDto update(@RequestBody UserDto userDto){
        if(userDto.getId() == null){
            throw  new IllegalArgumentException("For user update id have to be not null");
        }
        return userService.save(userDto);

    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        userService.deleteById(id);
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
