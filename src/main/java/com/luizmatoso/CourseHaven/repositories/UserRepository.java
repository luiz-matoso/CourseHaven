package com.luizmatoso.CourseHaven.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmatoso.CourseHaven.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
