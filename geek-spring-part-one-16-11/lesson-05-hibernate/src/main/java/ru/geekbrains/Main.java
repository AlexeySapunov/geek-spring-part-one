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

//        em.createQuery("select p " +
//                        "from Customer c " +
//                        "join c.attitudes at " +
//                        "join at.product p " +
//                        "where c.id = :customerId")
//                .setParameter("customerId", 1L)
//                .getResultList();

        em.createQuery("select c " +
                        "from Product p " +
                        "join p.attitudes at " +
                        "join at.customer c " +
                        "where p.id = :productId")
                .setParameter("productId", 1L)
                .getResultList();

        em.close();
    }
}
