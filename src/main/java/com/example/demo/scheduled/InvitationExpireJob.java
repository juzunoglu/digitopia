package com.example.demo.scheduled;

import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.repo.InvitationResponseRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    public void task1() {
        int result = 0 ;
        System.out.println(Thread.currentThread().getName()+" Task 1 executed at "+ new Date());
        if (invitationResponseRepo.existsByInvitationStatus(Invitation_Status.PENDING)) {
            result = invitationResponseRepo.updateInvitationStatusByInvitationStatus(Invitation_Status.PENDING);
            log.info("Expired invitations: {}", result);
        }
        log.info("No invitation was in pending state, updated: {}", result);
    }
}
