package ru.geekbrains.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<Attitude> attitudes;

    public Customer(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Customer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Attitude> getAttitudes() {
        return attitudes;
    }

    public void setAttitudes(List<Attitude> attitudes) {
        this.attitudes = attitudes;
    }
}
