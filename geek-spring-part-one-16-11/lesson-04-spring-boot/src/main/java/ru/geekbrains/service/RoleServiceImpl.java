package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.persist.RoleSpecification;
import ru.geekbrains.service.dto.RoleDto;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<RoleDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort) {
        Specification<Role> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(RoleSpecification.nameLike(nameFilter.get()));
        }
        return roleRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                .map(RoleServiceImpl::convertToDto);
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return roleRepository.findById(id).map(RoleServiceImpl::convertToDto);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = new Role(
                roleDto.getId(),
                roleDto.getName()
        );

        Role saved = roleRepository.save(role);
        return convertToDto(saved);

    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);

    }
    private static RoleDto convertToDto(Role role) {
        return new RoleDto
                (role.getId(),role.getName());
    }
}
