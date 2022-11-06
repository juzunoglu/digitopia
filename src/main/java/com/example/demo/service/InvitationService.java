package com.example.demo.service;

import com.example.demo.entity.Invitation;
import com.example.demo.model.InvitationDTO;
import com.example.demo.model.RespondInvitationDTO;

public interface InvitationService {

    Invitation inviteUser(InvitationDTO invitation);

    Invitation respondToInvitation(RespondInvitationDTO respondInvitationDTO);

}
