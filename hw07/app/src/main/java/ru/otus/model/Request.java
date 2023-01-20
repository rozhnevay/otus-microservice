package ru.otus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "t_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @Column(unique = true, nullable = false)
    private UUID id;
}
