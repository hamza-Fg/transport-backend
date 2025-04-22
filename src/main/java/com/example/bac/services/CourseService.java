package com.example.bac.services;

import com.example.bac.entities.Course;
import com.example.bac.entities.Localisation;
import com.example.bac.entities.Camion;
import com.example.bac.repositories.CourseRepository;
import com.example.bac.repositories.LocalisationRepository;
import com.example.bac.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LocalisationRepository localisationRepository;

    @Autowired
    private CamionRepository camionRepository;

    // 🔹 Créer une course et l'associer à un camion spécifique
    public Course saveCourseForCamion(Long camionId, Course course) {
        Optional<Camion> camion = camionRepository.findById(camionId);
        if (!camion.isPresent()) {
            throw new RuntimeException("Camion non trouvé avec l'ID : " + camionId);
        }

        // Lier les localisations (si elles existent) à la course
        if (course.getDepart() != null) {
            Localisation depart = localisationRepository.save(course.getDepart());
            course.setDepart(depart);
        }

        if (course.getArrivee() != null) {
            Localisation arrivee = localisationRepository.save(course.getArrivee());
            course.setArrivee(arrivee);
        }

        // Associer la course au camion
        course.setCamion(camion.get());

        return courseRepository.save(course);
    }

    // 🔹 Créer une course sans associer de camion
    public Course saveCourse(Course course) {
        // Lier les localisations (si elles existent)
        if (course.getDepart() != null) {
            Localisation depart = localisationRepository.save(course.getDepart());
            course.setDepart(depart);
        }

        if (course.getArrivee() != null) {
            Localisation arrivee = localisationRepository.save(course.getArrivee());
            course.setArrivee(arrivee);
        }

        return courseRepository.save(course);
    }

    // 🔹 Récupérer toutes les courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 🔹 Récupérer une course par ID
    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);  // Renvoie null si la course n'est pas trouvée
    }

    // 🔹 Récupérer toutes les courses d'un camion
    public List<Course> getCoursesByCamionId(Long camionId) {
        return courseRepository.findByCamionId(camionId);
    }

    // 🔹 Mettre à jour une course
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> course = courseRepository.findById(id);
        if (!course.isPresent()) {
            return null; // Course non trouvée
        }

        Course existingCourse = course.get();

        // Mettre à jour les champs de la course existante
        existingCourse.setDepart(courseDetails.getDepart());
        existingCourse.setArrivee(courseDetails.getArrivee());

        // Mettre à jour le camion si nécessaire
        if (courseDetails.getCamion() != null) {
            existingCourse.setCamion(courseDetails.getCamion());
        }

        return courseRepository.save(existingCourse);
    }

    // 🔹 Supprimer une course
    public void deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.delete(course.get());
        }
    }
}
