package com.example.finance_tracker.services;

import com.example.finance_tracker.DAOs.UserDao;
import com.example.finance_tracker.DTOs.UserRegistrationDto;
import com.example.finance_tracker.DTOs.UserUpdateDto;
import com.example.finance_tracker.entities.Role;
import com.example.finance_tracker.entities.User;
import com.example.finance_tracker.exceptions.EmailAlreadyExistsException;
import com.example.finance_tracker.exceptions.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegistrationDto dto){
    if(existsUserByEmail(dto.getEmail())){
        throw new EmailAlreadyExistsException("User with this email already exists!");
        }

    User user = new User();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    user.setRole(Role.USER);
    userDao.saveUser(user);
    return user;
    }

    @Override
    public User updateUser(Long id,UserUpdateDto dto){
    Optional<User> exist =  userDao.getUserById(id);
    User user = exist.orElseThrow(()->
            new UserNotFoundException("User not found!")
    );

    if(!user.getEmail().equals(dto.getEmail())
            && existsUserByEmail(dto.getEmail())){
        throw new EmailAlreadyExistsException("Email is already in use!");
    }

    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail());

    if(!dto.getPassword().isBlank()){
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    userDao.updateUser(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPassword()
    );

    return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email){
    return userDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id){
        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
    return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByEmail(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteUser(Long id){
        userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userDao.deleteUserById(id);
    }

}
