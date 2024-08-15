package com.luizmatoso.CourseHaven.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String demoVideo;
    private String category;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Language language;
    
    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    public int getLessonCount() {
        return lessons != null ? lessons.size() : 0;
    }

}
