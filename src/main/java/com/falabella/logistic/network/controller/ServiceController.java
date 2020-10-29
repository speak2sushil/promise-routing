package com.falabella.logistic.network.controller;

import com.falabella.logistic.network.dto.ServiceDto;
import com.falabella.logistic.network.model.Service;
import com.falabella.logistic.network.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.of;
import static org.springframework.http.ResponseEntity.of;

@RestController
@RequestMapping(value = "/api/v1")
@AllArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping(value = "/service")
    public ResponseEntity<Service> addService(@RequestBody ServiceDto serviceDto) {
        return of(of(serviceService.saveService(serviceDto)));
    }
}
