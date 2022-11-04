package com.example.demo.service.impl;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.User_Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
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
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found"));

        user.setStatus(User_Status.DELETED);
        user.setOrganizationSet(null); // todo
        user.setInvitation(null);
        userRepo.save(user);
        // todo delete organization?
        return true;
    }

    @Override
    public User updateByUserId(String id, User user) {
        User toBeUpdated = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("User is not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll(); // todo do a pagination later
    }

    @Override
    public List<Organization> getAllOrganizationsForUser(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found"));
        return user.getOrganizationSet().stream().toList();
    }
}
