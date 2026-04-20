package com.example.commerical_rent.repository;

import com.example.commerical_rent.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
    // Custom query methods can be added here if needed
}
