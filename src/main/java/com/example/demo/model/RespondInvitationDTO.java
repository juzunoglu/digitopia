package com.example.demo.model;

import com.example.demo.entity.enums.Invitation_Status;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record RespondInvitationDTO(

        @NotBlank(message = "User id cannot be null")
        @Schema(description = "User that will respond to the invitation",
                name = "userId",
                required = true,
                type = "string",
                example = "d0ad05e1-5cc7-4b54-accc-f24c35bb3797")
        String userId,

        @Schema(description = "Invitation that a user responds to",
                name = "invitationId",
                required = true,
                type = "string",
                example = "d0ad05e1-5cc7-4b54-accc-f24c35bb3797")
        String invitationId,

        @Schema(description = "User response for the specified invitation",
                name = "response",
                required = true,
                type = "enum",
                defaultValue = "REJECTED"
        )
        Invitation_Status response
) {
}
