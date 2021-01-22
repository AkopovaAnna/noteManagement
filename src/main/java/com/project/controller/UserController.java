package com.project.controller;

import com.project.dto.UserDto;
import com.project.model.Credentials;
import com.project.security.JwtService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demo/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("")
    public List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource save(@RequestBody UserDto user) {
        UserDto user1 = userService.createUser(user);
        return new UserResource(user1.getEmail(), user1.getFullName())
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable("id") Integer id, @RequestBody UserDto user) {
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }


    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public String createToken(@RequestBody Credentials credentials) {
        UserDto st = userService.checkAuthentication(credentials.getEmail(), credentials.getPassword());
        return jwtService.createToken(st.getUserId(), st.getEmail());
    }
}
