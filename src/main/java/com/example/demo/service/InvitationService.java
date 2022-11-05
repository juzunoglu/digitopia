package com.example.demo.service;

import com.example.demo.entity.Invitation;
import com.example.demo.entity.enums.Invitation_Status;

import java.util.List;

public interface InvitationService {

    Invitation sendInvitation(Invitation invitation);

    boolean expireInvitation(String id);

    void expireInvitationsJob();

    Invitation getById(String id);

    List<Invitation> getAllInvitations();

    Invitation rejectInvitation(String invitationId, Invitation_Status status);

}
