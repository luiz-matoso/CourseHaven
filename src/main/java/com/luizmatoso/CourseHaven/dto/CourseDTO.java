package com.luizmatoso.CourseHaven.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String demoVideo;
    private String category;
    private LanguageDTO language;
    private UserDTO createdBy;
    private Integer lessonCount;
}
