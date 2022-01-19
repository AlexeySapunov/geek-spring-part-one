package ru.geekbrains.entity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(long id);

    void save(User user);

    void update(User user);

    void delete(long id);
}
