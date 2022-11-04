package com.example.demo.service;

import com.example.demo.entity.Organization;

import java.util.List;

public interface OrganizationService {

    Organization saveOrganization(Organization organization);

    boolean deleteOrganizationById(String id);

    Organization updateOrganizationById(String id, Organization organization);

    Organization getById(String id);

    List<Organization> getAllOrganizations();
}
