package com.example.finance_tracker.services;

import com.example.finance_tracker.DTOs.UserRegistrationDto;
import com.example.finance_tracker.DTOs.UserUpdateDto;
import com.example.finance_tracker.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(UserRegistrationDto dto);

    User updateUser(Long id, UserUpdateDto dto);

    Optional<User> findUserByEmail(String email);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    boolean existsUserByEmail(String email);

    void deleteUser(Long id);
}
