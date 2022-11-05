package com.example.demo.converter;

import com.example.demo.entity.Invitation;
import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.model.InvitationDTO;

import java.time.LocalDateTime;
import java.util.UUID;


public class InvitationConverter {

    public static Invitation convertToEntity(InvitationDTO invitationDTO) {
        String invitationId = UUID.randomUUID().toString();
        return Invitation.builder()
                .id(invitationId)
                .createdOn(LocalDateTime.now())
                .createdBy(invitationId)
                .message(invitationDTO.invitationMessage())
                .invitationStatus(Invitation_Status.PENDING)
                .user(UserConverter.convertToEntity(invitationDTO.userId()))
                .build();
    }

}
