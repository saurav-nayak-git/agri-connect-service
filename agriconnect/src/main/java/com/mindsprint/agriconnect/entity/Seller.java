package com.mindsprint.agriconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String company;

    @Column(length = 10)
    private String phoneNumber;

    @Column(unique = true, length = 100)
    private String email;

    @OneToMany(mappedBy = "seller")
    private List<Order> orders = new ArrayList<>();

}