package com.example.bac.services;

import com.example.bac.entities.Camion;
import com.example.bac.entities.Course;
import com.example.bac.entities.Localisation;
import com.example.bac.entities.OptimalRouteResponse;
import com.example.bac.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RouteOptimizationService {

    @Autowired
    private CamionRepository camionRepository;

    public OptimalRouteResponse optimizeRoute(Long camionId) {
        // Récupérer le camion par son ID
        Camion camion = camionRepository.findById(camionId)
                .orElseThrow(() -> new RuntimeException("Camion non trouvé avec l'ID: " + camionId));

        List<Course> courses = camion.getCourses();
        if (courses == null || courses.isEmpty()) {
            return new OptimalRouteResponse(camionId, camion.getNomCamion(), Collections.emptyList(), 0.0);
        }

        // Point de départ (supposons une position initiale à l'origine)
        Localisation depart = new Localisation();
        depart.setX(0);
        depart.setY(0);

        // Calculer l'ordre optimal des courses avec A*
        List<Course> ordreOptimal = calculerOrdreOptimalAStar(depart, courses);

        // Calculer la distance totale
        double distanceTotale = calculerDistanceTotale(depart, ordreOptimal);

        // Préparer la réponse
        List<Long> ordreCoursesIds = ordreOptimal.stream()
                .map(Course::getId)
                .toList();

        return new OptimalRouteResponse(camionId, camion.getNomCamion(), ordreCoursesIds, distanceTotale);
    }

    private List<Course> calculerOrdreOptimalAStar(Localisation positionInitiale, List<Course> courses) {
        List<Course> result = new ArrayList<>();
        List<Course> remaining = new ArrayList<>(courses);
        Localisation currentPosition = positionInitiale;

        while (!remaining.isEmpty()) {
            Course nextCourse = trouverProchaineCourseSuivantAStar(currentPosition, remaining);
            result.add(nextCourse);
            remaining.remove(nextCourse);
            // Après avoir complété une course, la position actuelle devient la position d'arrivée
            currentPosition = nextCourse.getArrivee();
        }

        return result;
    }

    private Course trouverProchaineCourseSuivantAStar(Localisation positionActuelle, List<Course> coursesRestantes) {
        Course meilleureCourse = null;
        double meilleurScore = Double.MAX_VALUE;

        for (Course course : coursesRestantes) {
            // Distance réelle du point actuel au départ de la course
            double g = calculerDistance(positionActuelle, course.getDepart());

            // Heuristique: estimez la distance totale du parcours après cette course
            double h = estimerCoutRestant(course, coursesRestantes);

            // Score f = g + h (selon A*)
            double f = g + h;

            if (f < meilleurScore) {
                meilleurScore = f;
                meilleureCourse = course;
            }
        }

        return meilleureCourse;
    }

    private double estimerCoutRestant(Course courseActuelle, List<Course> coursesRestantes) {
        if (coursesRestantes.size() <= 1) {
            return 0;
        }

        // Une simple heuristique: la distance moyenne entre tous les points restants
        double totalDistance = 0;
        int count = 0;

        // Calculer la distance moyenne entre le point d'arrivée de la course actuelle et
        // les points de départ des courses restantes
        for (Course course : coursesRestantes) {
            if (course != courseActuelle) {
                totalDistance += calculerDistance(courseActuelle.getArrivee(), course.getDepart());
                count++;
            }
        }

        return count > 0 ? totalDistance / count : 0;
    }

    private double calculerDistance(Localisation point1, Localisation point2) {
        // Distance euclidienne entre deux points
        int dx = point1.getX() - point2.getX();
        int dy = point1.getY() - point2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double calculerDistanceTotale(Localisation positionInitiale, List<Course> courses) {
        double distanceTotale = 0;
        Localisation position = positionInitiale;

        for (Course course : courses) {
            // Distance du point actuel au départ de la course
            distanceTotale += calculerDistance(position, course.getDepart());
            // Distance du départ à l'arrivée de la course
            distanceTotale += calculerDistance(course.getDepart(), course.getArrivee());
            // Mise à jour de la position actuelle
            position = course.getArrivee();
        }

        return distanceTotale;
    }
}
