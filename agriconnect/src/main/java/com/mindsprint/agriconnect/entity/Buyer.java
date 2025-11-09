package com.mindsprint.agriconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "buyer",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_buyer_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_buyer_phone_number", columnNames = {"phone"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne
    @MapsId
    private User user;

    @Column(length = 10)
    private String phoneNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "buyer", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
}
