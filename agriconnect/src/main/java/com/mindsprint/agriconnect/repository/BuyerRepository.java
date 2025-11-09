package com.mindsprint.agriconnect.repository;

import com.mindsprint.agriconnect.entity.Buyer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findByName(String name);

    List<Buyer> findByBirthDateOrEmail(LocalDate birthDate, String email);

    List<Buyer> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    List<Buyer> findByNameContainingOrderByIdDesc(String query);

    @Query(value = "select * from Buyer", nativeQuery = true)
    Page<Buyer> findAllBuyers(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Buyer p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT p FROM Buyer p LEFT JOIN FETCH p.orders")
    List<Buyer> findAllBuyersWithOrders();

}
