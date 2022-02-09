package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.dto.RoleDto;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/role")
public class RoleResource {

    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Page<RoleDto> search(@RequestParam("nameFilter") Optional<String> nameFilter,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("sort") Optional<String> sort){
        return roleService.findAll(
                nameFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sort.filter(s -> !s.isBlank()).orElse("id"));
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/{id}")
    public  RoleDto findOne(@PathVariable("id") Long id){
        return roleService.findById(id)
                .orElseThrow(()-> new NotFoundException("Role with id " + id + "not found"));

    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@RequestBody RoleDto roleDto){
        if(roleDto.getId() != null){
            throw  new IllegalArgumentException("For role creation id have to be null");
        }
        return roleService.save(roleDto);
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PutMapping
    public RoleDto update(@RequestBody RoleDto roleDto){
        if(roleDto.getId() == null){
            throw  new IllegalArgumentException("For role update id have to be not null");
        }
        return roleService.save(roleDto);

    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        roleService.deleteById(id);
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
