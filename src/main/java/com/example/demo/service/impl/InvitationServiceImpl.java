package com.example.demo.service.impl;

import com.example.demo.converter.InvitationConverter;
import com.example.demo.entity.Invitation;
import com.example.demo.entity.InvitationResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.InvitationAlreadyRejectedException;
import com.example.demo.exception.InvitationIsInPendingException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.InvitationDTO;
import com.example.demo.model.RespondInvitationDTO;
import com.example.demo.repo.InvitationRepo;
import com.example.demo.repo.InvitationResponseRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.InvitationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@Transactional
@Slf4j
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepo invitationRepo;
    private final InvitationResponseRepo invitationResponseRepo;
    private final UserRepo userRepo;

    public InvitationServiceImpl(InvitationRepo invitationRepo, InvitationResponseRepo invitationResponseRepo, UserRepo userRepo) {
        this.invitationRepo = invitationRepo;
        this.invitationResponseRepo = invitationResponseRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Invitation inviteUser(InvitationDTO invitation) {
        Invitation invitation1 = InvitationConverter.convertToEntity(invitation);
        User user = userRepo.findById(invitation.userId())
                .orElseThrow(() -> new ResourceNotFoundException("No user is found with id: " + invitation.userId()));

        if (invitationResponseRepo.existsByUser_IdAndInvitationStatus(user.getId(), Invitation_Status.PENDING)) {
            throw new InvitationIsInPendingException("User still have not responded to the previous invitation");
        }
        if (invitationResponseRepo.existsByUser_IdAndInvitationStatus(user.getId(), Invitation_Status.REJECTED)) {
            throw new InvitationAlreadyRejectedException("User had already rejected the invitation");
        }
        InvitationResponse invitationResponse = InvitationResponse.builder()
                .invitationStatus(Invitation_Status.PENDING)
                .user(user)
                .invitation(invitation1)
                .build();

        user.setStatus(User_Status.INVITED);
        invitationRepo.save(invitation1);
        invitationResponseRepo.save(invitationResponse);

        return invitation1;
    }

    @Override
    public Invitation respondToInvitation(RespondInvitationDTO respondInvitationDTO) {
        Invitation invitation = invitationRepo.findById(respondInvitationDTO.invitationId())
                .orElseThrow(() -> new ResourceNotFoundException("No invitation found with id: " + respondInvitationDTO.invitationId()));
        User user = userRepo.findById(respondInvitationDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("No user is found with id: " + respondInvitationDTO.userId()));

        InvitationResponse invitationResponse = InvitationResponse.builder()
                .invitation(invitation)
                .user(user)
                .invitationStatus(respondInvitationDTO.response())
                .build();

        invitationResponseRepo.save(invitationResponse);

        return invitation;
    }
}
