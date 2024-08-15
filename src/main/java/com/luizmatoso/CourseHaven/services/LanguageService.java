package com.luizmatoso.CourseHaven.services;

import com.luizmatoso.CourseHaven.dto.LanguageDTO;
import com.luizmatoso.CourseHaven.entities.Language;
import com.luizmatoso.CourseHaven.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<LanguageDTO> findAll() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
    }

    private LanguageDTO convertToDTO(Language language) {
        LanguageDTO dto = new LanguageDTO();
        dto.setId(language.getId());
        dto.setName(language.getName());
        return dto;
    }
}
