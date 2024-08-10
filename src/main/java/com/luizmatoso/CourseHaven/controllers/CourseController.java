package com.luizmatoso.CourseHaven.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.User;
import com.luizmatoso.CourseHaven.services.CourseService;
import com.luizmatoso.CourseHaven.services.UserService;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showCourses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "website/home";
    }

    @GetMapping("/courses/{id}")
    public String viewCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        return "course-details"; 
    }

    @GetMapping("/courses/create")
    public String showCreateCourseForm(Model model, @RequestParam(required = false) Long courseId) {
        Course course;
        if (courseId != null) {
            course = courseService.findCourseById(courseId);
        } else {
            course = new Course();
        }
        model.addAttribute("course", course);
        return "website/post-course";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute Course course, Principal loggedUser) {
        User user = userService.findByUsername(loggedUser.getName());
        course.setCreatedBy(user);
        courseService.saveCourse(course, user.getId());
        return "redirect:/home";
    }

}
