package com.example.commerical_rent.repository;

import com.example.commerical_rent.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Custom query methods can be added here if needed
}
