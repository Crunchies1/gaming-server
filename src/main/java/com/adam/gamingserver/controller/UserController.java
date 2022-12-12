package com.adam.gamingserver.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adam.gamingserver.model.User;
import com.adam.gamingserver.services.UserService;

@RestController
@RequestMapping(path="/user") // This means URL's start with /user (after Application path)
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/status")
	public ResponseEntity<String> status () {
		return new ResponseEntity<>("User service up!", HttpStatus.OK);
	}

    @PostMapping(path="/add") // Map ONLY POST Requests
    public ResponseEntity<User> addUser (@RequestParam String email, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User newUser = new User(email, password);
        try {
            User savedUser = userService.save(newUser);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in adding user.", e);
		}
    }

    @PostMapping(path="/delete") // Map ONLY POST Requests
    public ResponseEntity<Boolean> deleteUser (@RequestParam long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            Optional<User> savedUser = userService.findOne(id);
            if (!savedUser.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with ID: " + id + " not found.");
            }
			return new ResponseEntity<>(userService.delete(savedUser.get()), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in deleting user.", e);
		}
    }

    @GetMapping(path="/get/{id}") // Map ONLY GET Requests
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            Optional<User> savedUser = userService.findOne(id);
            if (!savedUser.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with ID: " + id + " not found.");
            }
			return new ResponseEntity<>(savedUser.get(), HttpStatus.FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in retrieving user.", e);
		}
    }

    @GetMapping(path="/list")
    public ResponseEntity<Iterable<User>> findUsers() {
		try {
			return new ResponseEntity<>(userService.findAll(), HttpStatus.FOUND);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in listing users." ,e);
		}
    }

    @PostMapping(path="/login/{id}") // Map ONLY POST Requests
    public ResponseEntity<Boolean> login (@PathVariable("id") long id, @RequestParam String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        try {
            Optional<User> savedUser = userService.findOne(id);
            if (!savedUser.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with ID: " + id + " not found.");
            }
			return new ResponseEntity<>(userService.delete(savedUser.get()), HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error in deleting user.", e);
		}
    }
}
