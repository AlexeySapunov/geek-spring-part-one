package ru.geekbrains.entity;

import ru.geekbrains.Main;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> findAll() {
        EntityManager entityManager = Main.managerFactory().createEntityManager();

        List<User> users = entityManager.createQuery("select u from User u", User.class)
                .getResultList();
        System.out.println(users);

        entityManager.close();

        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        EntityManager entityManager = Main.managerFactory().createEntityManager();

        User user = entityManager.find(User.class, id);
        System.out.println(user);

        entityManager.close();

        return Optional.ofNullable(user);
    }

    @Override
    public void save(User user) {
        EntityManager entityManager = Main.managerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @Override
    public void update(User user) {
        EntityManager entityManager = Main.managerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @Override
    public void delete(long id) {
        EntityManager entityManager = Main.managerFactory().createEntityManager();

        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
