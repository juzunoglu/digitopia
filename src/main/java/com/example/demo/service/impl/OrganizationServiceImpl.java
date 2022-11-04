package com.example.demo.service.impl;

import com.example.demo.entity.Organization;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.OrganizationRepo;
import com.example.demo.service.OrganizationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepo organizationRepo;

    public OrganizationServiceImpl(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }


    @Override
    public Organization saveOrganization(Organization organization) {
        return organizationRepo.save(organization);
    }

    @Override
    public boolean deleteOrganizationById(String id) {
        Organization organization = organizationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));

//        organization.removeUser(organization.getUserSet()); todo
        organizationRepo.delete(organization);
        return true;
    }

    @Override
    public Organization updateOrganizationById(String id, Organization organization) {
        Organization organizationToBeUpdated = organizationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization is not found"));

        organizationToBeUpdated.setName(organization.getName());
        organizationToBeUpdated.setCompanySize(organization.getCompanySize());
        organizationToBeUpdated.setRegistryNumber(organization.getRegistryNumber()); // todo this should be unique handle that
        organizationToBeUpdated.setYearFounded(organization.getYearFounded());
        organizationToBeUpdated.setUpdatedBy(organization.getId());
        organizationToBeUpdated.setUpdatedOn(LocalDateTime.now());
        organizationToBeUpdated.setContactEmail(organization.getContactEmail());

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
}
