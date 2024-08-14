package com.luizmatoso.CourseHaven.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.User;
import com.luizmatoso.CourseHaven.repositories.CourseRepository;
import com.luizmatoso.CourseHaven.repositories.UserRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Course> findAll(){
        return courseRepository.findAll();
    }

    public Course findCourseById(Long id){
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> getCoursesByUser(User user) {
        return courseRepository.findByCreatedBy(user);
    }

    public void saveCourse(Course course, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        course.setCreatedBy(user);
        courseRepository.save(course);
    }

    public List<Course> findCoursesByUser(Long userId) {
        return courseRepository.findByCreatedById(userId);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }    

}
