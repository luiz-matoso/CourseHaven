package com.luizmatoso.CourseHaven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmatoso.CourseHaven.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    void deleteByCourseId(Long courseId);
}
