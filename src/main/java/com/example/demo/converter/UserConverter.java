package com.example.demo.converter;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.model.UserDTO;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.UUID;

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

    private static String normalizeName(String fullName) { // todo?
        return Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("\\d", "") // remove all numbers
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase(); // remove accents

    }
}