package com.example.demo.service;

import com.example.demo.entity.Organization;
import com.example.demo.entity.User;

import java.util.List;
public interface UserService {

    User saveUser(User user);

    boolean deleteByUserId(String id);

    User updateByUserId(String id, User user);

    User getByUserId(String id);

    List<User> getAllUsers();

    List<Organization> getAllOrganizationsForUser(String userId);
}
