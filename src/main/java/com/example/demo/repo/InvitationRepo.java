package com.example.demo.repo;


import com.example.demo.entity.Invitation;
import com.example.demo.entity.enums.Invitation_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvitationRepo extends JpaRepository<Invitation, String> {
    @Modifying
    @Query("update Invitation i set i.invitationStatus = 'EXPIRED' where i.invitationStatus = ?1")
    int updateInvitationStatusByInvitationStatus(Invitation_Status invitationStatus);

    boolean existsByInvitationStatus(Invitation_Status invitationStatus);


}
