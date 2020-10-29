package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.ServiceDto;
import com.falabella.logistic.network.model.Service;
import com.falabella.logistic.network.repository.ServiceRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService{

    private final ServiceRepository serviceRepository;

    @Override
    public Service saveService(ServiceDto serviceDto) {
        Service service = Service.builder()
                .serviceType(serviceDto.getServiceType())
                .enabled(serviceDto.isEnabled())
                .offeredDaysAhead(serviceDto.getOfferedDaysAhead())
                .serviceCategory(serviceDto.getServiceCategory())
                .build();
        return serviceRepository.save(service);
    }

    @Override
    public Optional<Service> findById(Long id) {
        return serviceRepository.findById(id);
    }
}
