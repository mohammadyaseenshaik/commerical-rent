package com.example.commerical_rent.services;

import com.example.commerical_rent.dtos.PropertyDTO;
import com.example.commerical_rent.entity.Property;
import com.example.commerical_rent.enums.PropertyStatus;
import com.example.commerical_rent.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PropertyDTO> getPropertyById(Long id) {
        return propertyRepository.findById(id).map(this::convertToDTO);
    }

    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Property property = convertToEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return convertToDTO(savedProperty);
    }

    public Optional<PropertyDTO> updateProperty(Long id, PropertyDTO propertyDTO) {
        if (propertyRepository.existsById(id)) {
            Property property = convertToEntity(propertyDTO);
            property.setId(id);
            Property updatedProperty = propertyRepository.save(property);
            return Optional.of(convertToDTO(updatedProperty));
        }
        return Optional.empty();
    }

    public boolean deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PropertyDTO convertToDTO(Property property) {
        return new PropertyDTO(
                property.getId(),
                property.getPropertyName(),
                property.getLocation(),
                property.getPropertyType(),
                property.getMonthlyRentAmount(),
                property.getOwner() != null ? property.getOwner().getId() : null,
                property.getAvailabilityStatus(),
                property.getCreatedAt()
        );
    }

    private Property convertToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setPropertyName(propertyDTO.getPropertyName());
        property.setLocation(propertyDTO.getLocation());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setMonthlyRentAmount(propertyDTO.getMonthlyRentAmount());
        // Note: Owner would need to be fetched from UserRepository, simplified here
        property.setAvailabilityStatus(propertyDTO.getAvailabilityStatus());
        return property;
    }
}
