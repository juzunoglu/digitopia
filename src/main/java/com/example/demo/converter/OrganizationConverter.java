package com.example.demo.converter;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.model.OrganizationDTO;
import com.example.demo.model.UserDTO;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.UUID;

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
                .name(organizationDTO.organizationName())
                .normalizedName(normalizeName(organizationDTO.organizationName()))
                .registryNumber(organizationDTO.registryNumber())
                .contactEmail(organizationDTO.concatEmail())
                .phone(organizationDTO.phone())
                .yearFounded(organizationDTO.yearFounded())
                .companySize(organizationDTO.companySize())
                .build();
    }

    private static String normalizeName(String fullName) { // todo?
        return Normalizer.normalize(fullName, Normalizer.Form.NFD)
                .replaceAll("\\d", "") // remove all numbers
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase(); // remove accents

    }
}
