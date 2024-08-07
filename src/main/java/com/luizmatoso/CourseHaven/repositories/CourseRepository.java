package com.luizmatoso.CourseHaven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmatoso.CourseHaven.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
