package com.luizmatoso.CourseHaven.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizmatoso.CourseHaven.dto.LessonDTO;
import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.Lesson;
import com.luizmatoso.CourseHaven.repositories.CourseRepository;
import com.luizmatoso.CourseHaven.repositories.LessonRepository;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    public void saveLesson(LessonDTO lessonDTO){
        Lesson lesson = new Lesson();
        lesson.setName(lessonDTO.getName());
        lesson.setLessonVideo(lessonDTO.getLessonVideo());

        Course course = courseRepository.findById(lessonDTO.getCourseId()).orElseThrow(() -> new RuntimeException("Course not found."));
        lesson.setCourse(course);

        lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLessonsByCourseId(Long courseId) {
        lessonRepository.deleteByCourseId(courseId);
    }
}
