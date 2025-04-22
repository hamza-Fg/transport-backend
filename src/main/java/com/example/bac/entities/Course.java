package com.example.bac.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation avec Camion
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "camion_id") // Colonne de clé étrangère
    private Camion camion;

    // Relation OneToOne avec Localisation pour le départ
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "depart_id", referencedColumnName = "id")
    private Localisation depart;

    // Relation OneToOne avec Localisation pour l'arrivée
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrivee_id", referencedColumnName = "id")
    private Localisation arrivee;

    // Constructor sans arguments
    public Course() {
    }

    // Constructor avec tous les arguments
    public Course(Long id, Camion camion, Localisation depart, Localisation arrivee) {
        this.id = id;
        this.camion = camion;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    // Getter et Setter pour id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter et Setter pour camion
    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    // Getter et Setter pour depart
    public Localisation getDepart() {
        return depart;
    }

    public void setDepart(Localisation depart) {
        this.depart = depart;
    }

    // Getter et Setter pour arrivee
    public Localisation getArrivee() {
        return arrivee;
    }

    public void setArrivee(Localisation arrivee) {
        this.arrivee = arrivee;
    }

    // ToString pour la visualisation facile
    @Override
    public String toString() {
        return "Course [id=" + id + ", camion=" + camion + ", depart=" + depart + ", arrivee=" + arrivee + "]";
    }

    // Equals et hashCode pour la comparaison d'objets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!id.equals(course.id)) return false;
        if (!camion.equals(course.camion)) return false;
        if (!depart.equals(course.depart)) return false;
        return arrivee.equals(course.arrivee);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + camion.hashCode();
        result = 31 * result + depart.hashCode();
        result = 31 * result + arrivee.hashCode();
        return result;
    }
}
