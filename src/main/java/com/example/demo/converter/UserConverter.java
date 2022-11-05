package com.example.demo.converter;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.model.UserDTO;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.demo.model.UserDTO.normalizeName;

public class UserConverter {

    public static UserDTO convertToDTO(User user) {
        return null;
    }

    public static User convertToEntity(UserDTO userDTO) {
        String userId = UUID.randomUUID().toString();
        return User.builder()
                .id(userId)
                .createdOn(LocalDateTime.now())
                .createdBy(userId)
                .fullName(userDTO.fullName())
                .status(User_Status.ACTIVE)
                .email(userDTO.email())
                .normalizedName(normalizeName(userDTO.fullName()))
                .build();
    }

    public static Set<User> convertToEntity(Set<UserDTO> userDTOS) {
        Set<User> result = new HashSet<>();

        userDTOS.forEach(userDTO -> {
            String userId = UUID.randomUUID().toString();
            User user = User.builder()
                    .id(userId)
                    .createdOn(LocalDateTime.now())
                    .createdBy(userId)
                    .fullName(userDTO.fullName())
                    .status(User_Status.ACTIVE)
                    .email(userDTO.email())
                    .normalizedName(normalizeName(userDTO.fullName()))
                    .build();

            result.add(user);
        });
        return result;
    }
}
