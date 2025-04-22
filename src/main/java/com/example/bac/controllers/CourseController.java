package com.example.bac.controllers;

import com.example.bac.entities.Course;
import com.example.bac.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 🔹 Ajouter une course à un camion spécifique
    @PostMapping("/camion/{camionId}")
    public ResponseEntity<Course> createCourseForCamion(@PathVariable Long camionId, @RequestBody Course course) {
        Course createdCourse = courseService.saveCourseForCamion(camionId, course);
        return ResponseEntity.ok(createdCourse);
    }

    // 🔹 Ajouter une course sans camion
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return ResponseEntity.ok(savedCourse);
    }

    // 🔹 Récupérer toutes les courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // 🔹 Récupérer une course par ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Récupérer toutes les courses d’un camion
    @GetMapping("/camion/{camionId}")
    public ResponseEntity<List<Course>> getCoursesByCamionId(@PathVariable Long camionId) {
        return ResponseEntity.ok(courseService.getCoursesByCamionId(camionId));
    }

    // 🔹 Modifier une course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        if (updatedCourse != null) {
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    // 🔹 Supprimer une course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
