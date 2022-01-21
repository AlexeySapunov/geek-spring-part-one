package ru.geekbrains;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        //INSERT 1
//        em.getTransaction().begin();
//
//        User user = new User(null, "ivan", "alex13");
//        em.persist(user);
//
//        List<Contact> contacts = new ArrayList<>();
//        contacts.add(new Contact(null, "home phone", "127897897", user));
//        contacts.add(new Contact(null, "work phone", "52313135437", user));
//        contacts.add(new Contact(null, "mobile phone", "565453487", user));
//        contacts.add(new Contact(null, "email", "ivan@mail.com", user));
//        contacts.forEach(em::persist);
//
//        em.getTransaction().commit();

        //INSERT 2 (with cascade)
//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 1L);
//        user.getContacts().add(new Contact(null, "home address", "NewTown, HomeStreet, 123/4", user));
//        em.merge(user);
//
//        em.getTransaction().commit();
//        em.close();

        //SELECT1
//        List<User> users = em.createQuery("" +
//                        "select distinct u " +
//                        "from User u " +
//                        "inner join u.contacts " +
//                        "where u.id = :id", User.class)
//                .setParameter("id", 1L)
//                .getResultList();
//        System.out.println("Users: " + users);

        //SELECT1
//        List<Contact> contacts = em.createQuery(
//                        "select distinct c " +
//                                "from Contact c " +
//                                "join fetch c.user " +
//                                "where c.type = :type", Contact.class)
//                .setParameter("type", "home phone")
//                .getResultList();
//
//        contacts.forEach(System.out::println);

        //DELETE1
//        em.getTransaction().begin();
//
//        em.createQuery("delete from Contact c where c.id = :id", Contact.class)
//                .setParameter("id", 1L)
//                .executeUpdate();
//
//        em.getTransaction().commit();

        //DELETE2 with orphanRemoval = true
//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 1L);
//        user.getContacts().remove(0);
//        em.merge(user);
//
//        em.getTransaction().commit();

        //DELETE2 with cascade delete
//        em.getTransaction().begin();
//
//        User user = em.find(User.class, 1L);
//        em.remove(user);
//
//        em.getTransaction().commit();

        em.close();
    }
}
