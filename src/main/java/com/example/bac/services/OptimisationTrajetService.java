package com.example.bac.services;

import com.example.bac.entities.Camion;
import com.example.bac.entities.Course;
import com.example.bac.entities.Localisation;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OptimisationTrajetService {

    /**
     * Optimise le trajet pour un camion en utilisant l'algorithme de Dijkstra
     * @param camion Le camion avec ses courses
     * @return Liste des courses ordonnées pour un trajet optimal
     */
    public List<Course> optimiserTrajet(Camion camion) {
        List<Course> courses = camion.getCourses();
        if (courses == null || courses.isEmpty()) {
            return new ArrayList<>();
        }

        // Créer un dépôt central (point de départ)
        Localisation depot = new Localisation();
        depot.setX(0);  // On suppose que le dépôt est à l'origine (0,0)
        depot.setY(0);

        // Créer le graphe représentant les distances entre les courses
        List<Localisation> points = new ArrayList<>();
        points.add(depot);  // Ajouter le dépôt comme premier point

        // Ajouter les points de départ et d'arrivée de chaque course
        for (Course course : courses) {
            points.add(course.getDepart());
            points.add(course.getArrivee());
        }

        // Calculer la matrice de distances entre tous les points
        int n = points.size();
        double[][] distances = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[i][j] = calculerDistance(points.get(i), points.get(j));
            }
        }

        // Utiliser Dijkstra pour trouver le chemin optimal
        List<Integer> cheminOptimal = trouverCheminOptimal(distances, 0);  // 0 est l'index du dépôt

        // Convertir les indices du chemin en courses ordonnées
        List<Course> coursesOptimisees = new ArrayList<>();

        // Pour chaque paire de points consécutifs dans le chemin optimal
        for (int i = 1; i < cheminOptimal.size(); i++) {
            int pointIndex = cheminOptimal.get(i);
            Localisation point = points.get(pointIndex);

            // Trouver la course correspondante à ce point
            for (Course course : courses) {
                if ((course.getDepart().equals(point) || course.getArrivee().equals(point))
                        && !coursesOptimisees.contains(course)) {
                    coursesOptimisees.add(course);
                    break;
                }
            }
        }

        return coursesOptimisees;
    }

    /**
     * Calcule la distance euclidienne entre deux localisations
     */
    private double calculerDistance(Localisation loc1, Localisation loc2) {
        int dx = loc1.getX() - loc2.getX();
        int dy = loc1.getY() - loc2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Trouve le chemin optimal en utilisant l'algorithme de Dijkstra
     * @param distances Matrice des distances entre les points
     * @param depart Index du point de départ
     * @return Liste des indices des points dans l'ordre optimal de visite
     */
    private List<Integer> trouverCheminOptimal(double[][] distances, int depart) {
        int n = distances.length;
        boolean[] visite = new boolean[n];
        double[] distancesTotales = new double[n];
        int[] predecesseurs = new int[n];

        // Initialiser les distances à l'infini et les prédécesseurs à -1
        Arrays.fill(distancesTotales, Double.POSITIVE_INFINITY);
        Arrays.fill(predecesseurs, -1);

        // La distance du point de départ à lui-même est 0
        distancesTotales[depart] = 0;

        for (int i = 0; i < n - 1; i++) {
            // Trouver le sommet non visité avec la distance minimale
            int u = trouverMinimum(distancesTotales, visite);
            visite[u] = true;

            // Mettre à jour les distances des sommets adjacents
            for (int v = 0; v < n; v++) {
                if (!visite[v] && distances[u][v] != 0 &&
                        distancesTotales[u] + distances[u][v] < distancesTotales[v]) {
                    distancesTotales[v] = distancesTotales[u] + distances[u][v];
                    predecesseurs[v] = u;
                }
            }
        }

        // Reconstruire le chemin optimal
        List<Integer> cheminOptimal = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (visite[i]) {
                cheminOptimal.add(i);
            }
        }

        // Trier le chemin en fonction des prédécesseurs
        Collections.sort(cheminOptimal, (a, b) -> {
            return Double.compare(distancesTotales[a], distancesTotales[b]);
        });

        return cheminOptimal;
    }

    /**
     * Trouve le sommet non visité avec la distance minimale
     */
    private int trouverMinimum(double[] distances, boolean[] visite) {
        double min = Double.POSITIVE_INFINITY;
        int minIndex = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visite[i] && distances[i] <= min) {
                min = distances[i];
                minIndex = i;
            }
        }

        return minIndex;
    }
}