package ru.otus.model;

import lombok.Data;
import org.hibernate.annotations.Generated;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
@Data
public class User {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Transient
    private String password;

    @Transient
    private String email;
}
