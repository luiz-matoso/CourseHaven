package com.luizmatoso.CourseHaven.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "teacher-management/teacher-management";
    }

    @GetMapping("/teacher/my/courses")
    public String showMyCourses(Model model, Principal loggedUser) {
        User user = userService.findByUsername(loggedUser.getName());
        List<Course> myCourses = courseService.findCoursesByUser(user.getId());
        model.addAttribute("myCourses", myCourses);
        return "teacher-management/my-courses";
    }

    @GetMapping("/teacher/create/courses")
    public String createCoursePage(Model model) {
        model.addAttribute("course", new Course());
        return "teacher-management/create-course";
    }

    @PostMapping("/teacher/create/courses")
    public String createCourse(@ModelAttribute Course course, Principal loggedUser) {
        User user = userService.findByUsername(loggedUser.getName());
        course.setCreatedBy(user);
        courseService.saveCourse(course, user.getId());
        return "redirect:/teacher/my/courses";
    }
}
