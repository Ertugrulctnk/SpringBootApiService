package com.example.project.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ID'nin otomatik olarak artmasını sağlıyoruz
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String location;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
