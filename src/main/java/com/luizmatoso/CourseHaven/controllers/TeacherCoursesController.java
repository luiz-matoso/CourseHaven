package com.luizmatoso.CourseHaven.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.luizmatoso.CourseHaven.dto.CourseDTO;
import com.luizmatoso.CourseHaven.dto.UserDTO;
import com.luizmatoso.CourseHaven.services.CourseService;
import com.luizmatoso.CourseHaven.services.LanguageService;
import com.luizmatoso.CourseHaven.services.UserService;

@Controller
public class TeacherCoursesController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LanguageService languageService;

    @GetMapping("/teacher/management")
    public String managementPage(Model model, Principal loggedUser) {
        if (loggedUser != null) {
            String username = loggedUser.getName();
            UserDTO userDTO = userService.findByUsername(username);
            model.addAttribute("firstName", userDTO.getFirstName());
        }

        UserDTO userDTO = userService.findByUsername(loggedUser.getName());
        List<CourseDTO> myCourses = courseService.findCoursesByUser(userDTO.getId());
        model.addAttribute("myCourses", myCourses);
        return "teacher-management/teachermanager";
    }

    @GetMapping("/teacher/my/courses")
    public String showMyCourses(Model model, Principal loggedUser) {
        UserDTO userDTO = userService.findByUsername(loggedUser.getName());
        List<CourseDTO> myCourses = courseService.findCoursesByUser(userDTO.getId());
        model.addAttribute("myCourses", myCourses);
        return "teacher-management/teachermanager";
    }

    @GetMapping("/teacher/create/courses")
    public String createCoursePage(Model model) {
        model.addAttribute("course", new CourseDTO());
        model.addAttribute("languages", languageService.findAll());
        return "teacher-management/create-course";
    }

    @PostMapping("/teacher/create/courses")
    public String createCourse(@ModelAttribute CourseDTO courseDTO, Principal loggedUser) {
        UserDTO userDTO = userService.findByUsername(loggedUser.getName());
        courseDTO.setCreatedBy(userDTO);
        courseService.saveCourse(courseDTO, userDTO.getId());
        return "redirect:/teacher/my/courses";
    }
    
}
