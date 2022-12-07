package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.User;
import ru.otus.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("{id}")
    User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).get();
    }

    @PostMapping
    User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }
}
