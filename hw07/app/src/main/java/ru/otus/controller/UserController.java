package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Request;
import ru.otus.model.User;
import ru.otus.repository.RequestRepository;
import ru.otus.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @GetMapping
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).get();
    }

    @PostMapping
    User createUser(@RequestBody User user, @RequestHeader("X-Request-Id") UUID requestId) {
        if (requestRepository.existsById(requestId)) {
            return user;
        }
        requestRepository.save(new Request(requestId));
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
