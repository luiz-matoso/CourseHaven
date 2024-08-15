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

import com.luizmatoso.CourseHaven.dto.CourseDTO;
import com.luizmatoso.CourseHaven.dto.UserDTO;
import com.luizmatoso.CourseHaven.services.CourseService;
import com.luizmatoso.CourseHaven.services.LanguageService;
import com.luizmatoso.CourseHaven.services.UserService;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private LanguageService languageService;

    @GetMapping("/home")
    public String showCourses(Model model, Principal loggedUser) {
        List<CourseDTO> courses = courseService.findAll();
        model.addAttribute("courses", courses);
    
        if (loggedUser != null) {
            String username = loggedUser.getName();
            UserDTO userDTO = userService.findByUsername(username);
            if (userDTO != null) {
                model.addAttribute("username", userDTO.getUsername()); 
                model.addAttribute("firstName", userDTO.getFirstName()); 
                model.addAttribute("lastName", userDTO.getLastName()); 
                model.addAttribute("userRole", userDTO.getUserRole().name());
            }
        }
    
        return "website/home";
    }
    

    @GetMapping("/courses/{id}")
    public String viewCourseDetails(@PathVariable Long id, Model model) {
        CourseDTO course = courseService.findCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("languages", languageService.findAll());
        return "course-details"; 
    }

    @GetMapping("/teacher/edit/course")
    public String showCreateCourseForm(Model model, @RequestParam(required = false) Long courseId) {
        CourseDTO course;
        if (courseId != null) {
            course = courseService.findCourseById(courseId);
        } else {
            course = new CourseDTO();
        }
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("course", course);
        return "teacher-management/edit-course";
    }    

    @PostMapping("/courses")
    public String createCourse(@ModelAttribute CourseDTO courseDTO, Principal loggedUser) {
        UserDTO userDTO = userService.findByUsername(loggedUser.getName());
        if (userDTO != null) {
            courseDTO.setCreatedBy(userDTO); 
            courseService.saveCourse(courseDTO, userDTO.getId());
        }
        return "redirect:/teacher/my/courses";
    }
    

    @GetMapping("/teacher/remove/course/{id}")
    public String removeCourse(@PathVariable Long id, Principal loggedUser) {
        UserDTO userDTO = userService.findByUsername(loggedUser.getName());
        CourseDTO course = courseService.findCourseById(id);
    
        if (course != null && course.getCreatedBy() != null && course.getCreatedBy().getId().equals(userDTO.getId())) {
            courseService.deleteCourseById(id);
        }
    
        return "redirect:/teacher/my/courses";
    }    
}
