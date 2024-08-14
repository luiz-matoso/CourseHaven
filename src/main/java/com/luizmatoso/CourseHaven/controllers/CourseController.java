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
    public String showCourses(Model model, Principal loggedUser) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);

        if (loggedUser != null){
            String username = loggedUser.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("firstName", user.getFirstName());
            model.addAttribute("userRole", user.getUserRole().name());
        }

        return "website/home";
    }

    @GetMapping("/courses/{id}")
    public String viewCourseDetails(@PathVariable Long id, Model model) {
        Course course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        return "course-details"; 
    }

    @GetMapping("/teacher/edit/course")
    public String showCreateCourseForm(Model model, @RequestParam(required = false) Long courseId) {
        Course course;
        if (courseId != null) {
            course = courseService.findCourseById(courseId);
        } else {
            course = new Course();
        }
        model.addAttribute("course", course);
        return "teacher-management/edit-course";
    }

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute Course course, Principal loggedUser) {
        User user = userService.findByUsername(loggedUser.getName());
        course.setCreatedBy(user);
        courseService.saveCourse(course, user.getId());
        return "redirect:/teacher/my/courses";
    }

    @GetMapping("/teacher/remove/course/{id}")
    public String removeCourse(@PathVariable Long id, Principal loggedUser){
        User user = userService.findByUsername(loggedUser.getName());
        Course course = courseService.findCourseById(id);

        if (course != null && course.getCreatedBy().getId().equals(user.getId())){
            courseService.deleteCourseById(id);
        }

        return "redirect:/teacher/my/courses";

    }
}
