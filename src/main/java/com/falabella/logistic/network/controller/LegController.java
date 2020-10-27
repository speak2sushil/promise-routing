package com.falabella.logistic.network.controller;


import com.falabella.logistic.network.dto.LegDTO;
import com.falabella.logistic.network.model.Leg;
import com.falabella.logistic.network.service.LegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/legs")
@Slf4j
public class LegController {

    @Autowired
    private LegService legService;

    @PostMapping()
    public ResponseEntity<Leg> createLeg(
            @RequestBody LegDTO legDTO) {
        log.info("LegController  {} createLeg start ");
        Leg savedLeg=legService.createLegAndNode(legDTO);
        log.info("LegController  {} createLeg end ");
        return new ResponseEntity<Leg>(savedLeg, HttpStatus.CREATED);
    }

    @PostMapping("/links")
    public ResponseEntity<Iterable<Leg>> createRelationshipBetweenNodes(
            @RequestBody List<LegDTO> legDTOList) {
        log.info("LegController  {} createRelationshipBetweenNodes start ");
        Iterable<Leg> legList=legService.linkNodes(legDTOList);
        log.info("LegController  {} createRelationshipBetweenNodes end ");
        return new ResponseEntity<Iterable<Leg>>(legList, HttpStatus.CREATED);
    }
}
