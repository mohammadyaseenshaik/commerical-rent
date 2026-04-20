package com.example.commerical_rent.services;

import com.example.commerical_rent.dtos.DisputeDTO;
import com.example.commerical_rent.entity.Dispute;
import com.example.commerical_rent.repository.DisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisputeService {

    @Autowired
    private DisputeRepository disputeRepository;

    public List<DisputeDTO> getAllDisputes() {
        return disputeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DisputeDTO> getDisputeById(Long id) {
        return disputeRepository.findById(id).map(this::convertToDTO);
    }

    public DisputeDTO createDispute(DisputeDTO disputeDTO) {
        Dispute dispute = convertToEntity(disputeDTO);
        Dispute savedDispute = disputeRepository.save(dispute);
        return convertToDTO(savedDispute);
    }

    public Optional<DisputeDTO> updateDispute(Long id, DisputeDTO disputeDTO) {
        if (disputeRepository.existsById(id)) {
            Dispute dispute = convertToEntity(disputeDTO);
            dispute.setId(id);
            Dispute updatedDispute = disputeRepository.save(dispute);
            return Optional.of(convertToDTO(updatedDispute));
        }
        return Optional.empty();
    }

    public boolean deleteDispute(Long id) {
        if (disputeRepository.existsById(id)) {
            disputeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private DisputeDTO convertToDTO(Dispute dispute) {
        return new DisputeDTO(
                dispute.getId(),
                dispute.getLeaseAgreement() != null ? dispute.getLeaseAgreement().getId() : null,
                dispute.getRaisedBy() != null ? dispute.getRaisedBy().getId() : null,
                dispute.getDisputeReason(),
                dispute.getDisputeStatus(),
                dispute.getResolutionRemark(),
                dispute.getResolvedBy() != null ? dispute.getResolvedBy().getId() : null,
                dispute.getCreatedAt(),
                dispute.getResolvedAt()
        );
    }

    private Dispute convertToEntity(DisputeDTO disputeDTO) {
        Dispute dispute = new Dispute();
        // Note: LeaseAgreement and Users would need to be fetched from respective repositories, simplified here
        dispute.setDisputeReason(disputeDTO.getDisputeReason());
        dispute.setDisputeStatus(disputeDTO.getDisputeStatus());
        dispute.setResolutionRemark(disputeDTO.getResolutionRemark());
        dispute.setResolvedAt(disputeDTO.getResolvedAt());
        return dispute;
    }
}
