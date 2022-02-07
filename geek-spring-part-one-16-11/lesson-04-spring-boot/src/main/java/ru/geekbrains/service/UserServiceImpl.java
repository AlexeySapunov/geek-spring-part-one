package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.persist.UserSpecification;
import ru.geekbrains.service.dto.UserDto;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Page<UserDto> findAll(Optional<String> nameFilter, Integer page, Integer size, String sort) {
        Specification<User> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(UserSpecification.nameLike(nameFilter.get()));
        }
        return userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sort)))
                .map(UserServiceImpl::convertToDto);
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserServiceImpl::convertToDto);
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getLogin(),
                userDto.getPassword()
        );

        User saved = userRepository.save(user);
        return convertToDto(saved);

    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);

    }
    private static UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getPassword()
        );
    }
}
