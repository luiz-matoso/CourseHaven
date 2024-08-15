package com.luizmatoso.CourseHaven.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luizmatoso.CourseHaven.dto.UserDTO;
import com.luizmatoso.CourseHaven.entities.User;
import com.luizmatoso.CourseHaven.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName()); 
        dto.setUserRole(user.getUserRole());
        return dto;
    }
    
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName()); 
        user.setUserRole(userDTO.getUserRole());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return user;
    }
    

    public void saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userRepository.save(user);
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? convertToDTO(user) : null;
    }

    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
