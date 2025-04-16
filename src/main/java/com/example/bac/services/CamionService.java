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

    public void deleteCamion(Long id) {
    camionRepository.deleteById(id);
    }

    public Camion updateCamion(Long id, Camion updatedCamion) {
    Camion existing = camionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Camion non trouvé"));
    existing.setNomCamion(updatedCamion.getNomCamion());
    // Ajouter d’autres champs si besoin
    return camionRepository.save(existing);
    }


}