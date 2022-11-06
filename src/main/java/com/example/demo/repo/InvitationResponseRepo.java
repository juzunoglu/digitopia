package com.example.demo.repo;

import com.example.demo.entity.InvitationResponse;
import com.example.demo.entity.enums.Invitation_Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvitationResponseRepo extends JpaRepository<InvitationResponse, String> {

    @Modifying
    @Query("update InvitationResponse i set i.invitationStatus = 'EXPIRED' where i.invitationStatus = ?1")
    int updateInvitationStatusByInvitationStatus(Invitation_Status invitationStatus);

    @Query("select (count(i) > 0) from InvitationResponse i where i.invitationStatus = ?1")
    boolean existsByInvitationStatus(Invitation_Status invitationStatus);

    @Query("select (count(i) > 0) from InvitationResponse i where i.user.id = ?1 and i.invitationStatus = ?2")
    boolean existsByUser_IdAndInvitationStatus(String id, Invitation_Status invitationStatus);



}
