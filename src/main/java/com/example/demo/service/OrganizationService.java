package com.example.demo.service;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface OrganizationService {

    Organization saveOrganization(Organization organization);

    boolean deleteOrganizationById(String id);

    Organization updateOrganizationById(String id, Organization organization);

    Organization getById(String id);

    List<Organization> getAllOrganizations();

    Organization assignUserToOrganization(User user, String organizationId);

    Organization removeUserFromOrganization(String organizationId, String userId);

    Set<User> usersUnderOrganization(String organizationId);

    Organization findByRegistryNumber(String registryNumber);

    List<Organization> searchByNormalizedNameYearSize(String normalizedName, Date year, Long size);

}
