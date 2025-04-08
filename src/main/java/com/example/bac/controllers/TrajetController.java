package com.example.bac.controllers;

import com.example.bac.entities.Camion;
import com.example.bac.entities.Course;
import com.example.bac.services.OptimisationTrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/trajets")
public class TrajetController {

    private final OptimisationTrajetService optimisationService;

    @Autowired
    public TrajetController(OptimisationTrajetService optimisationService) {
        this.optimisationService = optimisationService;
    }

    /**
     * Endpoint pour optimiser le trajet d'un camion
     * @param camion Le camion avec ses courses à optimiser
     * @return Liste ordonnée des courses optimisées
     */
    @PostMapping("/optimiser")
    public ResponseEntity<List<Course>> optimiserTrajet(@RequestBody Camion camion) {
        List<Course> coursesOptimisees = optimisationService.optimiserTrajet(camion);
        return ResponseEntity.ok(coursesOptimisees);
    }
}