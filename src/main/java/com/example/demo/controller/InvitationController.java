package com.example.demo.controller;

import com.example.demo.entity.Invitation;
import com.example.demo.model.InvitationDTO;
import com.example.demo.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return new ResponseEntity<>(invitationService.inviteUser(invitationDTO), HttpStatus.CREATED);
    }

}
