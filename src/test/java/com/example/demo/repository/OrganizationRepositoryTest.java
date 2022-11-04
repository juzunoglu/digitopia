package com.example.demo.repository;

import com.example.demo.entity.Organization;
import com.example.demo.repo.OrganizationRepo;
import com.example.demo.service.OrganizationService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepo organizationRepo;

    @AfterAll
    public void clearDb() {
        organizationRepo.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        organizationRepo.deleteAll();
    }

    @Test
    public void saveOrganization() {
        Organization organization = createOrganization("amadeus", "amadeus@gmail.com", "AMB12", "+90 552 704 05 84");
        assertEquals(organization, organizationService.getById(organization.getId()));
    }


    // helper methods
    private Organization createOrganization(String name, String email, String registryNumber, String phone) {
        Organization organization = Organization.builder()
                .id(UUID.randomUUID().toString())
                .registryNumber(registryNumber)
                .contactEmail(email)
                .yearFounded(new Date())
                .phone(phone)
                .companySize(55L)
                .build();

        return organizationRepo.save(organization);
    }
}
