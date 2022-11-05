package com.example.demo.controller;

import com.example.demo.converter.InvitationConverter;
import com.example.demo.entity.Invitation;
import com.example.demo.entity.enums.Invitation_Status;
import com.example.demo.model.InvitationDTO;
import com.example.demo.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/invitation")
@Slf4j
public class InvitationController {

    private final InvitationService invitationService;


    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Operation(summary = "Invite user")
    @PostMapping(path = "/invite")
    public ResponseEntity<Invitation> inviteUser(@RequestBody @Valid InvitationDTO invitationDTO) {
        log.info("saveInvitation is called with: {}", invitationDTO);
        return new ResponseEntity<>(invitationService.sendInvitation(InvitationConverter.convertToEntity(invitationDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Invitation by Id")
    @GetMapping(path = "/{invitationId}")
    public ResponseEntity<Invitation> getInvitationById(@PathVariable String invitationId) {
        log.info("getInvitationById is called with: {}", invitationId);
        return new ResponseEntity<>(invitationService.getById(invitationId), HttpStatus.OK);
    }

    @Operation(summary = "Expire the invitation for testing purposes")
    @DeleteMapping(path = "/{invitationId}")
    public ResponseEntity<Boolean> deleteInvitationById(@PathVariable String invitationId) {
        log.info("deleteInvitationById is called with: {}", invitationId);
        return new ResponseEntity<>(invitationService.expireInvitation(invitationId), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get All Invitations")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        log.info("getAllInvitations is called");
        return new ResponseEntity<>(invitationService.getAllInvitations(), HttpStatus.OK);
    }


    @Operation(summary = "Reject an Invitation")
    @PutMapping(path = "/reject/{invitationId}")
    public ResponseEntity<Invitation> rejectInvitation(@PathVariable String invitationId, @RequestParam Invitation_Status invitationStatus) {
        log.info("respondToAnInvitation is called");
        return new ResponseEntity<>(invitationService.rejectInvitation(invitationId, invitationStatus), HttpStatus.OK);
    }

}
