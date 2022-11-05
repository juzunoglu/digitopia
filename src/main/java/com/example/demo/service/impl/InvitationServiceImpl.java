package com.example.demo.service.impl;

import com.example.demo.entity.Invitation;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.InvitationRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.InvitationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.entity.enums.Invitation_Status.EXPIRED;
import static com.example.demo.entity.enums.Invitation_Status.PENDING;

@Service
@Transactional
@Slf4j
public class InvitationServiceImpl implements InvitationService {


    private final InvitationRepo invitationRepo;
    private final UserRepo userRepo;

    public InvitationServiceImpl(InvitationRepo invitationRepo, UserRepo userRepo) {
        this.invitationRepo = invitationRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Invitation sendInvitation(Invitation invitation) {
        User userTobeInvited = userRepo.findById(invitation.getUser().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("No user is found with id: " + invitation.getUser().getId()));

        userTobeInvited.setStatus(User_Status.INVITED);
        invitation.setUser(userTobeInvited);
        return invitationRepo.save(invitation);
    }

    @Override
    public boolean expireInvitation(String id) {
        Invitation invitation = invitationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation is not found with id: " + id));

        invitation.setInvitationStatus(EXPIRED);
        invitationRepo.save(invitation);
        return true;
    }

    @Override
    @Scheduled(cron = "0 * * * * *", zone = "UTC")
    public void expireInvitationsJob() {
        log.info("Job started");
        if (invitationRepo.existsByInvitationStatus(PENDING)) {
            int updatedEntries = invitationRepo.updateInvitationStatusByInvitationStatus(PENDING);
            log.info("Updated entries: {}", updatedEntries);
        }
        log.info("If no invitation is in pending, no need to run the sql update");
    }

    @Override
    public Invitation getById(String id) {
        return invitationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation is not found with id: " + id));
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return invitationRepo.findAll();
    }

    @Override
    public Invitation rejectInvitation(String invitationId, Invitation_Status status) {
        Invitation invitation1 = invitationRepo.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invitation is not found with id: " + invitationId));

        invitation1.setInvitationStatus(status);
        return invitationRepo.save(invitation1);
    }
}
