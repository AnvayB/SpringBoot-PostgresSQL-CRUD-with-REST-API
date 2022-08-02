package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

//    get users
    @GetMapping("/employees")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    get user by id
    @GetMapping("/employees/{emailId}")
    public ResponseEntity<User> getUserByEmailId(@PathVariable(value = "emailId") String userEmail) {
        User user = userRepository.findByEmailId(userEmail);
        return ResponseEntity.ok().body(user);
    }

//    save user
    @PostMapping("/employees")
    public User createUser(@RequestBody User user) {
        logger.info("Create Users");
        return userRepository.save(user);
    }

//    update user
    @PutMapping("/employees/{emailId}")
    public ResponseEntity<User> updateUsers(@PathVariable(value = "emailId") String userEmail,
                                               @Validated @RequestBody User userDetails) {
        User user = userRepository.findByEmailId(userEmail);
        user.setEmailId(userDetails.getEmailId());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

//    delete user
    @DeleteMapping("/employees/{emailId}")
    public Map<String, Boolean> deleteUsers(@PathVariable(value = "emailId") String userEmail) {
        User user = userRepository.findByEmailId(userEmail);
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}