package com.mindsprint.agriconnect.repository;

import com.mindsprint.agriconnect.entity.Buyer;
import com.mindsprint.agriconnect.entity.Seller;
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
public interface SellerRepository extends JpaRepository<Buyer, Long> {
    Seller findByName(String name);

    List<Seller> findByBirthDateOrEmail(LocalDate birthDate, String email);

    List<Seller> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    List<Seller> findByNameContainingOrderByIdDesc(String query);

    @Query(value = "select * from Seller", nativeQuery = true)
    Page<Seller> findAllSellers(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Seller p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

    @Query("SELECT p FROM Seller p LEFT JOIN FETCH p.orders")
    List<Seller> findAllSellersWithOrders();
}
