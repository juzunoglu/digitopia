package com.example.demo.service.impl;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.RegistryNumberAlreadyExistsException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.OrganizationRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.OrganizationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepo organizationRepo;

    private final UserRepo userRepo;

    public OrganizationServiceImpl(OrganizationRepo organizationRepo, UserRepo userRepo) {
        this.organizationRepo = organizationRepo;
        this.userRepo = userRepo;
    }


    @Override
    public synchronized Organization saveOrganization(Organization organization) {
        checkOrganizationValid(organization);
        return organizationRepo.save(organization);
    }

    private void checkOrganizationValid(Organization organization) {
        if (organizationRepo.existsByRegistryNumber(organization.getRegistryNumber())) {
            throw new RegistryNumberAlreadyExistsException("Registry number is already taken: " + organization.getRegistryNumber());
        }
        organization.getUserSet().forEach(user -> {
            if (userRepo.existsByEmail(user.getEmail())) {
                throw new EmailAlreadyExistsException("Email is already taken: " + user.getEmail());
            }
        });
    }

    private void checkUserValid(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already taken: " + user.getEmail());
        }
    }

    @Override
    public boolean deleteOrganizationById(String id) {
        Organization organization = organizationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));

        organizationRepo.delete(organization);
        return true;
    }

    @Override
    public synchronized Organization updateOrganizationById(String id, Organization organization) {
        Organization organizationToBeUpdated = organizationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));

        checkOrganizationValid(organization);

        organizationToBeUpdated.setName(organization.getName());
        organizationToBeUpdated.setCompanySize(organization.getCompanySize());
        organizationToBeUpdated.setRegistryNumber(organization.getRegistryNumber());
        organizationToBeUpdated.setYearFounded(organization.getYearFounded());
        organizationToBeUpdated.setUpdatedBy(organization.getId());
        organizationToBeUpdated.setUpdatedOn(LocalDateTime.now());
        organizationToBeUpdated.setContactEmail(organization.getContactEmail());
        organization.getUserSet().forEach(organizationToBeUpdated::addUser);

        return organizationRepo.save(organizationToBeUpdated);
    }

    @Override
    public Organization getById(String id) {
        return organizationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));

    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepo.findAll();
    }

    @Override
    public Organization assignUserToOrganization(User user, String organizationId) {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("No organization with id: " + organizationId));

        checkUserValid(user);
        organization.addUser(user);
        return organizationRepo.save(organization);
    }

    @Override
    public Organization removeUserFromOrganization(String organizationId, String userId) {
        Organization organization = organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("No organization with id: " + organizationId));

        organization.removeUser(userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user is found to delete: " + userId))
        );
        return organizationRepo.save(organization);
    }

    @Override
    public Set<User> usersUnderOrganization(String organizationId) {
        return organizationRepo.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"))
                .getUserSet();
    }

    @Override
    public Organization findByRegistryNumber(String registryNumber) {
        return organizationRepo.findByRegistryNumber(registryNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));
    }

    @Override
    public List<Organization> searchByNormalizedNameYearSize(String normalizedName, Date year, Long size) {
        return organizationRepo.findByNormalizedNameContainsIgnoreCaseOrYearFoundedOrCompanySize(normalizedName, year, size);
    }
}
