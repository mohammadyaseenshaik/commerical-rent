package com.example.commerical_rent.services;

import com.example.commerical_rent.dtos.RentPaymentDTO;
import com.example.commerical_rent.entity.RentPayment;
import com.example.commerical_rent.repository.RentPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentPaymentService {

    @Autowired
    private RentPaymentRepository rentPaymentRepository;

    public List<RentPaymentDTO> getAllRentPayments() {
        return rentPaymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RentPaymentDTO> getRentPaymentById(Long id) {
        return rentPaymentRepository.findById(id).map(this::convertToDTO);
    }

    public RentPaymentDTO createRentPayment(RentPaymentDTO rentPaymentDTO) {
        RentPayment rentPayment = convertToEntity(rentPaymentDTO);
        RentPayment savedRentPayment = rentPaymentRepository.save(rentPayment);
        return convertToDTO(savedRentPayment);
    }

    public Optional<RentPaymentDTO> updateRentPayment(Long id, RentPaymentDTO rentPaymentDTO) {
        if (rentPaymentRepository.existsById(id)) {
            RentPayment rentPayment = convertToEntity(rentPaymentDTO);
            rentPayment.setId(id);
            RentPayment updatedRentPayment = rentPaymentRepository.save(rentPayment);
            return Optional.of(convertToDTO(updatedRentPayment));
        }
        return Optional.empty();
    }

    public boolean deleteRentPayment(Long id) {
        if (rentPaymentRepository.existsById(id)) {
            rentPaymentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RentPaymentDTO convertToDTO(RentPayment rentPayment) {
        return new RentPaymentDTO(
                rentPayment.getId(),
                rentPayment.getLeaseAgreement() != null ? rentPayment.getLeaseAgreement().getId() : null,
                rentPayment.getAmount(),
                rentPayment.getPaymentMonth(),
                rentPayment.getPaymentDate(),
                rentPayment.getPaymentStatus(),
                rentPayment.getPenaltyAmount(),
                rentPayment.getReferenceId(),
                rentPayment.getCreatedAt()
        );
    }

    private RentPayment convertToEntity(RentPaymentDTO rentPaymentDTO) {
        RentPayment rentPayment = new RentPayment();
        // Note: LeaseAgreement would need to be fetched from LeaseAgreementRepository, simplified here
        rentPayment.setAmount(rentPaymentDTO.getAmount());
        rentPayment.setPaymentMonth(rentPaymentDTO.getPaymentMonth());
        rentPayment.setPaymentDate(rentPaymentDTO.getPaymentDate());
        rentPayment.setPaymentStatus(rentPaymentDTO.getPaymentStatus());
        rentPayment.setPenaltyAmount(rentPaymentDTO.getPenaltyAmount());
        rentPayment.setReferenceId(rentPaymentDTO.getReferenceId());
        return rentPayment;
    }
}
