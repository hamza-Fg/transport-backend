package com.example.bac.entities;

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

    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camion camion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_id", referencedColumnName = "id")
    private Localisation depart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivee_id", referencedColumnName = "id")
    private Localisation arrivee;
}