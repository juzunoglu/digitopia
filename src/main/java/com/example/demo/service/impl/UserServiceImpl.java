package com.example.demo.service.impl;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;


    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public boolean deleteByUserId(String id) {
        User user = userRepo.findNonDeletedUser(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + id));

        user.setStatus(User_Status.DELETED);
        userRepo.save(user);
        return true;
    }

    @Override
    public User updateByUserId(String id, User user) {
        User toBeUpdated = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + id));

        toBeUpdated.setFullName(user.getFullName());
        // todo cannot use the already existing email. they should be unique
        toBeUpdated.setEmail(user.getEmail());
        toBeUpdated.setUpdatedBy(user.getId());
        toBeUpdated.setUpdatedOn(LocalDateTime.now());
        return userRepo.save(toBeUpdated);
    }

    @Override
    public User getByUserId(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll(); // todo do a pagination later
    }

    @Override
    public List<Organization> getAllOrganizationsForUser(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + userId));
        return user.getOrganizationSet().stream().toList();
    }

    @Override
    public User searchByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with email: " + email));
    }

    @Override
    public List<User> searchByNormalizedName(String normalizedName) {
        return userRepo.findByNormalizedNameContainsIgnoreCase(normalizedName); // -> bad solution, actually needs lucene/elastichsearch/solr but no time
    }
}
