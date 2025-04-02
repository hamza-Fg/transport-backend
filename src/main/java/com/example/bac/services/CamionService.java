package com.example.bac.services;

import com.example.bac.entities.Camion;
import com.example.bac.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamionService {

    @Autowired
    private CamionRepository camionRepository;

    public Camion saveCamion(Camion camion) {
        return camionRepository.save(camion);
    }

    public List<Camion> getAllCamions() {
        return camionRepository.findAll();
    }

    public Camion getCamionById(Long id) {
        return camionRepository.findById(id).orElse(null);
    }
}