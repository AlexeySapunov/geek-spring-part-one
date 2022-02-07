package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.service.dto.RoleDto;

import java.util.Optional;

public interface RoleService {

    Page<RoleDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort);

    Optional<RoleDto> findById(Long id);

    RoleDto save(RoleDto roleDto);

    void deleteById(Long id);
}
