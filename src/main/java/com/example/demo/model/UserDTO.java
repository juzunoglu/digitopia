package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.Normalizer;

public record UserDTO(

        @NotBlank(message = "User name cannot be null or empty")
        @Size(min = 3, max = 30, message = "User name must be in between 3 and 30 characters")
        @Schema(description = "Full name of the user",
                name = "fullName",
                required = true,
                type = "string",
                example = "Alihan Uzunoglu")
        String fullName,

        @Schema(description = "Email of the user",
                name = "email",
                required = true,
                example = "uzunoglualihan@gmail.com")
        @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
        String email
) {

    public static String normalizeName(String fullName) { // todo?
        return Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("\\d", "") // remove all numbers
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase(); // remove accents
    }
}
