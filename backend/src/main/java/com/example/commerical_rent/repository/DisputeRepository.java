package com.example.commerical_rent.repository;

import com.example.commerical_rent.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisputeRepository extends JpaRepository<Dispute, Long> {
    // Custom query methods can be added here if needed
}
