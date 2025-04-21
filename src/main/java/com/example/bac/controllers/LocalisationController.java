package com.example.bac.controllers;

import com.example.bac.entities.Localisation;
import com.example.bac.repositories.LocalisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/localisations")
public class LocalisationController {

    @Autowired
    private LocalisationRepository localisationRepository;

    @GetMapping
    public List<Localisation> getAllLocalisations() {
        return localisationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localisation> getLocalisationById(@PathVariable Long id) {
        return localisationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Localisation createLocalisation(@RequestBody Localisation localisation) {
        return localisationRepository.save(localisation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Localisation> updateLocalisation(@PathVariable Long id, @RequestBody Localisation updated) {
        return localisationRepository.findById(id).map(existing -> {
            existing.setX(updated.getX());
            existing.setY(updated.getY());
            return ResponseEntity.ok(localisationRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalisation(@PathVariable Long id) {
        if (!localisationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localisationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
