package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    @AfterAll
    public void clearDb() {
        userRepo.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        userRepo.deleteAll();
    }

    @Test
    public void saveUser() {
        User user = createUser("alihan", "ali123@gmail.com");
        assertEquals(user, userService.getByUserId(user.getId()));
    }


    @Test
    public void updateUser() {
        User oldUser = createUser("nesrin", "nesrin@gmail.com");
        User newUser = User.builder().fullName("faruk").email("faruk@gmail.com").build();

        assertNull(oldUser.getUpdatedBy());

        userService.updateByUserId(oldUser.getId(), newUser);
        User updatedUser = userService.getByUserId(oldUser.getId());
        System.out.println(updatedUser);
        assertEquals("faruk", updatedUser.getFullName());
    }

    @Test
    public void saveUserWithOrganization() {

    }

    @Test
    public void deleteUser() {
        User user = createUser("ekincan", "ekin@gmail.com");
        userRepo.deleteById(user.getId());
        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    public void getAllUsers() {
        for (int i = 0; i < 20; i++) {
            createUser(randomNameGenerator(), randomEmailGenerator());
        }
        assertEquals(20, userRepo.findAll().size());
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
}
