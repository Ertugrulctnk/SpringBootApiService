package com.example.project.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String species;
    private String breed;//cins
    private int age;
    private String gender;
    private String personality;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}
