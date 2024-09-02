package com.eduardo.gerenciador_tarefas_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.gerenciador_tarefas_api.models.User;
import com.eduardo.gerenciador_tarefas_api.models.UserRequestDTO;
import com.eduardo.gerenciador_tarefas_api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAll(Pageable page) {
		List<User> users = userService.getAll(page).getContent();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@PostMapping
	public ResponseEntity<User> create(@RequestBody UserRequestDTO userRequest) {
		User createdUser = userService.create(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<User> update(@PathVariable("userId") String userId, @RequestBody UserRequestDTO userRequest) {
		userService.update(userId, userRequest);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<User> delete(@PathVariable("userId") String userId) {
		userService.delete(userId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
