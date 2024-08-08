package com.luizmatoso.CourseHaven.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmatoso.CourseHaven.entities.Course;
import com.luizmatoso.CourseHaven.entities.User;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCreatedBy(User createdby);
}
