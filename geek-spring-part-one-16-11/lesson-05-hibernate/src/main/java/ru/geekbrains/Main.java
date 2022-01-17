package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.User;
import ru.geekbrains.entity.UserRepositoryImpl;

import javax.persistence.EntityManagerFactory;

public class Main {

    static EntityManagerFactory emFactory;

    public static void main(String[] args) {
        emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        
        UserRepositoryImpl user = new UserRepositoryImpl();

        user.save(new User(null, "alex2", "alex123"));
        user.save(new User(null, "alex3", "alex456"));
        user.save(new User(null, "alex4", "alex789"));

        user.findAll();

        user.findById(1L);

        User user1 = new User(2L, "alex6", "alex23");
        user.update(user1);

        user.delete(3L);
    }

    public static EntityManagerFactory managerFactory() {
        return emFactory;
    }
}
