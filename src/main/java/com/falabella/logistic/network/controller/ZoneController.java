package com.falabella.logistic.network.controller;

import com.falabella.logistic.network.dto.ItineraryDTO;
import com.falabella.logistic.network.service.ZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/zones")
@Slf4j
public class ZoneController {


    @Autowired
    private ZoneService zoneService;


    @GetMapping("/facilities")
    public ResponseEntity<List<ItineraryDTO>> findItinerary(
            @RequestParam(value = "zoneIds") List<Long> zoneIds) {
        log.info("ZoneController  {} findItinerary start ");
        List<ItineraryDTO> response=zoneService.findItinerary(zoneIds);
        log.info("ZoneController  {} findItinerary end ");
        return new ResponseEntity<List<ItineraryDTO>>(response, HttpStatus.OK);
    }


}
