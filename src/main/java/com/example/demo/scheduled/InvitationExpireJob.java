package com.example.demo.scheduled;

import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.repo.InvitationResponseRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@EnableScheduling
@Service
@Slf4j
public class InvitationExpireJob {

    private final InvitationResponseRepo invitationResponseRepo;

    public InvitationExpireJob(InvitationResponseRepo invitationResponseRepo) {
        this.invitationResponseRepo = invitationResponseRepo;
    }

    @Scheduled(cron = "@weekly")
    @Async
    public void expirePendingInvitationsWeekly() {
        int result = 0 ;
        if (invitationResponseRepo.existsByInvitationStatus(Invitation_Status.PENDING)) {
            result = invitationResponseRepo.updateInvitationStatusByInvitationStatus(Invitation_Status.PENDING);
            log.debug("Expired invitations: {}", result);
        }
        log.debug("No invitation was in pending state, updated: {}", result);
    }
}
