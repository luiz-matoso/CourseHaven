package com.luizmatoso.CourseHaven.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmatoso.CourseHaven.dto.CourseDTO;
import com.luizmatoso.CourseHaven.dto.LanguageDTO;
import com.luizmatoso.CourseHaven.dto.UserDTO;
import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.Language;
import com.luizmatoso.CourseHaven.entities.User;
import com.luizmatoso.CourseHaven.repositories.CourseRepository;
import com.luizmatoso.CourseHaven.repositories.UserRepository;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CourseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        return course != null ? convertToDTO(course) : null;
    }

    @Transactional
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }    

    @Transactional
    public void saveCourse(CourseDTO courseDTO, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Course course;
        if (courseDTO.getId() != null) {
            course = courseRepository.findById(courseDTO.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
            course.setTitle(courseDTO.getTitle());
            course.setDescription(courseDTO.getDescription());
            course.setDemoVideo(courseDTO.getDemoVideo());
            course.setCategory(courseDTO.getCategory());
    
            if (courseDTO.getLanguage() != null) {
                Language language = new Language();
                language.setId(courseDTO.getLanguage().getId());
                language.setName(courseDTO.getLanguage().getName());
                course.setLanguage(language);
            }
            
            course.setCreatedBy(user); 
        } else {
            course = convertToEntity(courseDTO);
            course.setCreatedBy(user);
        }
        
        courseRepository.save(course);
    }    

    public List<CourseDTO> getCoursesByUser(User user) {
        return courseRepository.findByCreatedBy(user)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> findCoursesByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return getCoursesByUser(user);
    }    

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setDemoVideo(course.getDemoVideo());
        courseDTO.setCategory(course.getCategory());

        if (course.getLanguage() != null) {
            LanguageDTO languageDTO = new LanguageDTO();
            languageDTO.setId(course.getLanguage().getId());
            languageDTO.setName(course.getLanguage().getName());
            courseDTO.setLanguage(languageDTO);
        }

        if (course.getCreatedBy() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(course.getCreatedBy().getId());
            userDTO.setUsername(course.getCreatedBy().getUsername());
            userDTO.setFirstName(course.getCreatedBy().getFirstName());
            userDTO.setLastName(course.getCreatedBy().getLastName());
            courseDTO.setCreatedBy(userDTO);
        }

        courseDTO.setLessonCount(course.getLessonCount());
        return courseDTO;
    }


    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setDemoVideo(courseDTO.getDemoVideo());
        course.setCategory(courseDTO.getCategory());

        if (courseDTO.getLanguage() != null) {
            Language language = new Language();
            language.setId(courseDTO.getLanguage().getId());
            language.setName(courseDTO.getLanguage().getName());
            course.setLanguage(language);
        }
        
        return course;
    }
    

}    
