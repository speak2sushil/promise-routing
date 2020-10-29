package com.falabella.logistic.network.service;

import com.falabella.logistic.network.dto.ServiceDto;
import com.falabella.logistic.network.model.Service;

import java.util.Optional;

public interface ServiceService {
    Service saveService(ServiceDto serviceDto);
    Optional<Service> findById(Long id);
}
