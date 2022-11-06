package com.example.demo.repository;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.InvitationRepo;
import com.example.demo.repo.InvitationResponseRepo;
import com.example.demo.repo.OrganizationRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.demo.helper.Helper.randomEmailGenerator;
import static com.example.demo.helper.Helper.randomNameGenerator;
import static com.example.demo.model.OrganizationDTO.normalizeCompanyName;
import static com.example.demo.model.UserDTO.normalizeName;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private InvitationResponseRepo invitationResponseRepo;

    @Autowired
    private UserRepo userRepo;

    @AfterAll
    public void clearDb() {
        invitationResponseRepo.deleteAll();
        invitationRepo.deleteAll();
        organizationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        invitationResponseRepo.deleteAll();
        invitationRepo.deleteAll();
        organizationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void saveOrganizationTest() {
        Organization organization = createOrganization("amadeus", "amadeus@gmail.com", "AMB12", "+90 552 704 05 84");
        assertEquals(organization, organizationService.getById(organization.getId()));
    }
    @Test
    public void deleteOrganizationByIdTest() {
        Organization organization = createOrganization("amadeus", "amadeus@gmail.com", "AMB12", "+90 552 704 05 84");
        organizationService.deleteOrganizationById(organization.getId());
        assertEquals(0, organizationRepo.findAll().size());
    }
    @Test
    public void getOrganizationByIdTest() {
        Organization organization = createOrganization("amadeus", "amadeus@gmail.com", "AMB12", "+90 552 704 05 84");
        assertEquals(organization, organizationService.getById(organization.getId()));

        String doesNotExistId = UUID.randomUUID().toString();
        assertThrows(ResourceNotFoundException.class,
                () -> organizationService.getById(doesNotExistId), "Organization is not found with id: doesNotExistId"
        );
    }
    @Test
    public void updateOrganizationByIdTest() {
        Organization oldOrganization = createOrganization("amadeus", "amadeus@gmail.com", "AMB12", "+90 552 704 05 84");
        Organization newOrganization = Organization.builder()
                .name("faruk").contactEmail("faruk@gmail.com").build();


        assertNull(oldOrganization.getUpdatedBy());

        organizationService.updateOrganizationById(oldOrganization.getId(), newOrganization);
        Organization updatedOrganization = organizationService.getById(oldOrganization.getId());
        assertEquals("faruk", updatedOrganization.getName());
    }

    @Test
    public void getAllOrganizationsTest() {
        for (int i = 0; i < 20; i++) {
            createOrganization(randomNameGenerator(), randomEmailGenerator(), randomNameGenerator(), "+905527040545");
        }
        assertEquals(20, organizationService.getAllOrganizations().size());
    }

    @Test
    @Transactional
    public void returnAllUsersUnderOrganizationTest() {
        Organization organization = createOrganizationWithUser("digi", "diggi@gmail.com", "dig12", "+905527040545");
        assertEquals(2, organizationService.usersUnderOrganization(organization.getId()).size());
    }

    @Test
    public void searchByRegistryNumberTest() {
        Organization organization1 = createOrganization("comp1", "comp1@gmail.com", "reg12", "+905527040545");
        Organization organization2 = createOrganization("comp2", "comp2@gmail.com", "reg22", "+905527040545");

        assertEquals(organization1, organizationService.findByRegistryNumber("reg12"));
        assertNotEquals(organization2, organizationService.findByRegistryNumber("reg12"));
    }

    @Test
    public void searchByNormalizedNameTest() {
        Organization organization1 = createOrganization("comp1", "comp1@gmail.com", "reg1", "+905527040545");
        Organization organization2 = createOrganization("comp2", "comp2@gmail.com", "reg2", "+905527040545");
        Organization organization3 = createOrganization("comp3", "comp3@gmail.com", "reg3", "+905527040545");
        Organization organization4 = createOrganization("12comp423", "comp4@gmail.com", "reg4", "+905527040545");
        Organization organization5 = createOrganization("notrelated", "comp5@gmail.com", "reg5", "+905527040545");

        List<Organization> res = organizationService.searchByNormalizedNameYearSize("comp", new Date(), 88L);
        assertEquals(4, res.size());
        assertTrue(res.containsAll(List.of(organization1, organization2, organization3, organization4)));
        assertFalse(res.contains(organization5));
    }
    @Test
    public void searchByNormalizedNameAndSizeTest() {
        Organization organization1 = createOrganization("comp1", "comp1@gmail.com", "reg1", "+905527040545");
        Organization organization2 = createOrganization("comp2", "comp2@gmail.com", "reg2", "+905527040545");
        Organization organization3 = createOrganization("comp3", "comp3@gmail.com", "reg3", "+905527040545");

        List<Organization> res = organizationService.searchByNormalizedNameYearSize("not-realated", new Date(), 55L);
        assertEquals(3, res.size());
        assertTrue(res.containsAll(List.of(organization1, organization2, organization3)));
    }


    // helper methods
    private Organization createOrganization(String name, String email, String registryNumber, String phone) {

        Organization organization = Organization.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .normalizedName(normalizeCompanyName(name))
                .registryNumber(registryNumber)
                .contactEmail(email)
                .yearFounded(new Date())
                .phone(phone)
                .companySize(55L)
                .build();

        return organizationRepo.save(organization);
    }

    private Organization createOrganizationWithUser(String name, String email, String registryNumber, String phone) {

        User user1 = createUser("nesrin", "nesrimngmail.com");
        User user2 = createUser("haktan", "haktan@gmail.com");

        Organization organization = Organization.builder()
                .id(UUID.randomUUID().toString())
                .registryNumber(registryNumber)
                .contactEmail(email)
                .userSet(Set.of(user1, user2))
                .yearFounded(new Date())
                .normalizedName(normalizeCompanyName(name))
                .phone(phone)
                .companySize(55L)
                .build();

        return organizationService.saveOrganization(organization);
    }

    private User createUser(String fullName, String email) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .status(User_Status.ACTIVE)
                .email(email)
                .fullName(fullName)
                .normalizedName(normalizeName(fullName))
                .build();
        return user;
    }
}
