package com.example.bac.repositories;

import com.example.bac.entities.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long> {
}
