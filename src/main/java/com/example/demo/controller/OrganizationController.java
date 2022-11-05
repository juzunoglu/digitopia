package com.example.demo.controller;

import com.example.demo.converter.OrganizationConverter;
import com.example.demo.converter.UserConverter;
import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.model.OrganizationDTO;
import com.example.demo.model.UserDTO;
import com.example.demo.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/v1/organization")
@Slf4j
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }


    @Operation(summary = "Save an organization")
    @PostMapping(path = "/save")
    public ResponseEntity<Organization> saveOrganization(@RequestBody @Valid OrganizationDTO organizationDTO) {
        log.info("saveOrganization is called with: {}", organizationDTO);
        return new ResponseEntity<>(organizationService.saveOrganization(OrganizationConverter.convertToEntity(organizationDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Organization by Id")
    @GetMapping(path = "/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String organizationId) {
        log.info("getOrganizationById is called with: {}", organizationId);
        return new ResponseEntity<>(organizationService.getById(organizationId), HttpStatus.OK);
    }

    @Operation(summary = "Delete Organization by Id")
    @DeleteMapping(path = "/{organizationId}")
    public ResponseEntity<Boolean> deleteOrganizationById(@PathVariable String organizationId) {
        log.info("deleteOrganizationById is called with: {}", organizationId);
        return new ResponseEntity<>(organizationService.deleteOrganizationById(organizationId), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update Organization by Id")
    @PutMapping(path = "/{organizationId}")
    public ResponseEntity<Organization> updateOrganizationById(@RequestBody @Valid OrganizationDTO organizationDTO, @PathVariable String organizationId) {
        log.info("updateOrganizationById is called with body: {}, and id: {}", organizationDTO, organizationId);
        return new ResponseEntity<>(
                organizationService.updateOrganizationById(organizationId, OrganizationConverter.convertToEntity(organizationDTO)), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Get All Organizations")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        log.info("getAllOrganizations is called");
        return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
    }

    @Operation(summary = "Return all users under an organization")
    @GetMapping(path = "/{organizationId}/users")
    public ResponseEntity<Set<User>> getAllUsersUnderAnOrganization(@PathVariable String organizationId) {
        log.info("getAllUsersUnderAnOrganization is called with : {}", organizationId);
        return new ResponseEntity<>(organizationService.usersUnderOrganization(organizationId), HttpStatus.OK);
    }

    @Operation(summary = "Add User to an organization")
    @PutMapping(path = "/{organizationId}/addUser")
    public ResponseEntity<Organization> assignUserToAnOrganization(@PathVariable String organizationId, @RequestBody @Valid UserDTO userDTO) {
        log.info("assignUserToAnOrganization is called with id: {} and user: {}", organizationId, userDTO);
        return new ResponseEntity<>(organizationService.assignUserToOrganization(UserConverter.convertToEntity(userDTO), organizationId), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Remove user from the organization")
    @PutMapping(path = "/{organizationId}/{userId}/removeUser")
    public ResponseEntity<Organization> removeUserFromAnOrganization(@PathVariable String organizationId, @PathVariable String userId) {
        log.info("removeUserFromAnOrganization is called with id: {} and user: {}", organizationId, userId);
        return new ResponseEntity<>(organizationService.removeUserFromOrganization(organizationId, userId), HttpStatus.ACCEPTED);
    }
}