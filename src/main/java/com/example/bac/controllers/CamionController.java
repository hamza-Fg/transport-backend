package com.example.bac.controllers;
import com.example.bac.entities.Camion;
import com.example.bac.services.CamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/camions")
public class CamionController {

    @Autowired
    private CamionService camionService;

    @PostMapping
    public Camion createCamion(@RequestBody Camion camion) {
        return camionService.saveCamion(camion);
    }

    @GetMapping
    public List<Camion> getAllCamions() {
        return camionService.getAllCamions();
    }

    @GetMapping("/{id}")
    public Camion getCamionById(@PathVariable Long id) {
        return camionService.getCamionById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCamion(@PathVariable Long id) {
    camionService.deleteCamion(id);
    }

    @PutMapping("/{id}")
    public Camion updateCamion(@PathVariable Long id, @RequestBody Camion updatedCamion) {
    return camionService.updateCamion(id, updatedCamion);
    }


}