package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record InvitationDTO(

        @NotBlank(message = "Invitation message cannot be null or empty")
        @Schema(description = "Invitation message to be sent to the user",
                name = "invitationMessage",
                required = true,
                type = "string",
                example = "This is a simple invitation message")
        String invitationMessage,

        @NotBlank(message = "User id cannot be null")
        @Schema(description = "User that will get the invitation",
                name = "userId",
                required = true,
                type = "string",
                example = "d0ad05e1-5cc7-4b54-accc-f24c35bb3797")
        String userId

) {
}
