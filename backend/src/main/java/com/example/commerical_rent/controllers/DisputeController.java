package com.example.commerical_rent.controllers;

import com.example.commerical_rent.dtos.DisputeDTO;
import com.example.commerical_rent.services.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @GetMapping
    public ResponseEntity<List<DisputeDTO>> getAllDisputes() {
        List<DisputeDTO> disputes = disputeService.getAllDisputes();
        return ResponseEntity.ok(disputes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisputeDTO> getDisputeById(@PathVariable Long id) {
        Optional<DisputeDTO> dispute = disputeService.getDisputeById(id);
        return dispute.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DisputeDTO> createDispute(@RequestBody DisputeDTO disputeDTO) {
        DisputeDTO createdDispute = disputeService.createDispute(disputeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDispute);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisputeDTO> updateDispute(@PathVariable Long id, @RequestBody DisputeDTO disputeDTO) {
        Optional<DisputeDTO> updatedDispute = disputeService.updateDispute(id, disputeDTO);
        return updatedDispute.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispute(@PathVariable Long id) {
        boolean deleted = disputeService.deleteDispute(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
