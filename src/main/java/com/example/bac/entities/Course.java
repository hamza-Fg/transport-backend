package com.example.bac.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data // Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Builder // Permet d'utiliser le pattern Builder
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camion camion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_id", referencedColumnName = "id")
    private Localisation depart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivee_id", referencedColumnName = "id")
    private Localisation arrivee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Localisation getDepart() {
        return depart;
    }

    public void setDepart(Localisation depart) {
        this.depart = depart;
    }

    public Localisation getArrivee() {
        return arrivee;
    }

    public void setArrivee(Localisation arrivee) {
        this.arrivee = arrivee;
    }
}