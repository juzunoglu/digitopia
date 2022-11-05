package com.example.demo.converter;

import com.example.demo.entity.Organization;
import com.example.demo.model.OrganizationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.demo.model.OrganizationDTO.normalizeCompanyName;

public class OrganizationConverter {

    public static OrganizationDTO convertToDTO(Organization organization) {
        return null;
    }

    public static Organization convertToEntity(OrganizationDTO organizationDTO) {
        String organizationId = UUID.randomUUID().toString();
        return Organization.builder()
                .id(organizationId)
                .createdOn(LocalDateTime.now())
                .createdBy(organizationId)
                .userSet(UserConverter.convertToEntity(organizationDTO.users()))
                .name(organizationDTO.organizationName())
                .normalizedName(normalizeCompanyName(organizationDTO.organizationName()))
                .registryNumber(organizationDTO.registryNumber())
                .contactEmail(organizationDTO.concatEmail())
                .phone(organizationDTO.phone())
                .yearFounded(organizationDTO.yearFounded())
                .companySize(organizationDTO.companySize())
                .build();
    }
}
