package com.project.service;

import com.project.dto.UserDto;
import com.project.exception.AuthenticationFailureException;
import com.project.exception.AuthorizationException;
import com.project.exception.ConflictException;
import com.project.repository.UserRepository;
import com.project.security.SecurityContextAccessor;
import com.project.security.UserDetails;
import com.project.system.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityContextAccessor contextAccessor;

    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    public UserDto createUser(UserDto user) {
        if (getUserByEmail(user.getEmail()).isPresent()) {
            throw new ConflictException("User by given email already exist");
        } else if (user.getPassword() != null) {
            user.setPassword(encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new NoSuchElementException("password missing");
        }
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("user not found"));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto updateUser(Integer id, UserDto user) {
        UserDetails userDetails = contextAccessor.getUserDetails();
        if (permissionEvaluator.hasUpdateUserPermission(userDetails, user)) {
            user.setUserId(id);
            UserDto oldUser = getUserById(id);
            if (oldUser != null) {
                oldUser = mappingOdEntityData(oldUser, user);

                return userRepository.save(oldUser);
            }
            throw new EntityNotFoundException("not exist user");
        }
        throw new AuthorizationException("you are not authorized");

    }


    public void delete(Integer id) {
        UserDetails userDetails = contextAccessor.getUserDetails();
        if (permissionEvaluator.hasDeleteUserPermission(userDetails, userDetails.getId())) {{
            userRepository.deleteById(id);
        }}
//        throw new AuthorizationException("you are not authorized");
    }

    public UserDto checkAuthentication(String email, String pass) {
        Optional<UserDto> st = getUserByEmail(email);
        if (st.isPresent()) {
            if (passwordEncoder.matches(pass, st.get().getPassword())) {
                return st.get();
            }
        }
        throw new AuthenticationFailureException("User email/password is incorrect.");
    }

    private String encode(String input) {
        return passwordEncoder.encode(input);
    }

    private UserDto mappingOdEntityData(UserDto oldUser, UserDto updatedUser) {
        oldUser.setEmail(updatedUser.getEmail());
        oldUser.setFullName(updatedUser.getFullName());
        oldUser.setUpdated(updatedUser.getUpdated());
        oldUser.setPassword(encode(updatedUser.getPassword()));

        return oldUser;
    }

}
