package com.example.bac.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "camions")
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_camion")
    private String nomCamion;

    @JsonManagedReference
    @OneToMany(mappedBy = "camion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCamion() {
        return nomCamion;
    }

    public void setNomCamion(String nomCamion) {
        this.nomCamion = nomCamion;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}