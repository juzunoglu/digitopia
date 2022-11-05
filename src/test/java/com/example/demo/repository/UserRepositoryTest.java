package com.example.demo.repository;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.OrganizationRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.OrganizationService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.example.demo.helper.Helper.randomEmailGenerator;
import static com.example.demo.helper.Helper.randomNameGenerator;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepo organizationRepo;

    @AfterAll
    public void clearDb() {
        organizationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        organizationRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void saveUserTest() {
        User user = createUser("alihan", "ali123@gmail.com");
        assertEquals(user, userService.getByUserId(user.getId()));
    }

    @Test
    public void getUserByIdTest() {
        User user = createUser("hakan", "hakan@gmail.com");
        assertEquals(user, userService.getByUserId(user.getId()));

        String doesNotExistId = UUID.randomUUID().toString();
        assertThrows(ResourceNotFoundException.class,
                () -> userService.getByUserId(doesNotExistId), "User is not found with id: doesNotExistId"
        );
    }

    @Test
    public void deleteUserByIdTest() {
        User user = createUser("ekincan", "ekin@gmail.com");
        userRepo.deleteById(user.getId());
        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    public void updateUserByIdTest() { //todo cannot update with an already existing email! do not forget that case!
        User oldUser = createUser("nesrin", "nesrin@gmail.com");
        User newUser = User.builder().fullName("faruk").email("faruk@gmail.com").build();

        assertNull(oldUser.getUpdatedBy());

        userService.updateByUserId(oldUser.getId(), newUser);
        User updatedUser = userService.getByUserId(oldUser.getId());
        System.out.println(updatedUser);
        assertEquals("faruk", updatedUser.getFullName());
    }


    @Test
    public void getAllUsersTest() {
        for (int i = 0; i < 20; i++) {
            createUser(randomNameGenerator(), randomEmailGenerator());
        }
        assertEquals(20, userRepo.findAll().size());
    }

    @Test
    public void getAllOrganizationThatUserBelongsTest() {
        Organization organization = createOrganizationWithUser("Comp", "comp@gmail.com", "COMP12", "+905527040545");
        List<String> userIdsInOrganization = organization.getUserSet().stream().map(user -> user.getId()).toList();
        String userId = userIdsInOrganization.stream().findAny().get();
        assertEquals(organization, userService.getAllOrganizationsForUser(userId).get(0));

    }

    @Test
    public void searchByEmailTest() {
        User user1 = createUser("ali", "search@gmail.com");

        assertEquals(user1, userService.searchByEmail("search@gmail.com"));
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.searchByEmail("ali@gmail.com");
        }, "User is not found with email: ali@gmail.com");
    }

    @Test
    public void searchByNormalizedNameTest() {
        User user1 = createUser("alis", "alis@gmail.com");
        User user2 = createUser("alim", "alim@gmail.com");
        User user3 = createUser("alik", "alik@gmail.com");
        User user4 = createUser("Ekincan", "ekincan@gmail.com");

        assertTrue(userService.searchByNormalizedName("ali").contains(user1));
        assertTrue(userService.searchByNormalizedName("ali").contains(user2));
        assertTrue(userService.searchByNormalizedName("ali").contains(user3));
        assertFalse(userService.searchByNormalizedName("ali").contains(user4));

        assertEquals(3, userService.searchByNormalizedName("ali").size());
    }


    // Database Helper Methods
    private User createUser(String fullName, String email) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .status(User_Status.ACTIVE)
                .email(email)
                .fullName(fullName)
                .normalizedName(fullName)
                .build();

        return userService.saveUser(user);
    }
    private Organization createOrganizationWithUser(String name, String email, String registryNumber, String phone) {

        User user1 = createUser("nesrin", "nesrimn@gmail.com");
        User user2 = createUser("haktan", "haktan@gmail.com");

        Organization organization = Organization.builder()
                .id(UUID.randomUUID().toString())
                .registryNumber(registryNumber)
                .contactEmail(email)
                .userSet(Set.of(user1, user2))
                .yearFounded(new Date())
                .phone(phone)
                .companySize(55L)
                .build();

        return organizationService.saveOrganization(organization);
    }
}
