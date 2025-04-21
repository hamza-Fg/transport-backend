package com.example.bac.services;

import com.example.bac.entities.Camion;
import com.example.bac.entities.Course;
import com.example.bac.repositories.CamionRepository;
import com.example.bac.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CamionRepository camionRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> getCoursesByCamionId(Long camionId) {
        return courseRepository.findByCamionId(camionId);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
    public Course saveCourseForCamion(Long camionId, Course course) {
        Camion camion = camionRepository.findById(camionId)
        .orElseThrow(() -> new RuntimeException("Camion non trouvé avec ID : " + camionId));
    course.setCamion(camion);
    return courseRepository.save(course);
}


    public Course updateCourse(Long id, Course courseDetails) {
        Course course = getCourseById(id);
        if (course != null) {
            course.setCamion(courseDetails.getCamion());
            course.setDepart(courseDetails.getDepart());
            course.setArrivee(courseDetails.getArrivee());
            return courseRepository.save(course);
        }
        return null;
    }
}