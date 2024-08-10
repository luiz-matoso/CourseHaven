package com.luizmatoso.CourseHaven.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.User;
import com.luizmatoso.CourseHaven.services.CourseService;
import com.luizmatoso.CourseHaven.services.UserService;

@Controller
public class TeacherCoursesController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/teacher/management")
    public String managementPage(Model model, Principal loggedUser) {
        if (loggedUser != null) {
            String username = loggedUser.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("firstName", user.getFirstName());
        }
        return "website/teacher-management";
    }

    @GetMapping("/teacher/my/courses")
    public String showMyCourses(Model model, Principal loggedUser) {
        User user = userService.findByUsername(loggedUser.getName());
        List<Course> myCourses = courseService.findCoursesByUser(user.getId());
        model.addAttribute("myCourses", myCourses);
        return "website/my-courses";
    }

    @GetMapping("/teacher/create/courses")
    public String createCoursePage() {
        return "website/create-course";
    }
}
