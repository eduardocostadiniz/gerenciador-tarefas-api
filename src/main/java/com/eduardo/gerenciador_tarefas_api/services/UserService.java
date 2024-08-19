package com.eduardo.gerenciador_tarefas_api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.gerenciador_tarefas_api.exceptions.UserAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.User;
import com.eduardo.gerenciador_tarefas_api.models.UserRequestDTO;
import com.eduardo.gerenciador_tarefas_api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User create(UserRequestDTO userRequest) {
		User user = userRepository.findByEmail(userRequest.email());

		if (user != null) {
			throw new UserAlreadyExistsException();
		}

		User newUser = new User(userRequest);
		newUser.setCreatedAt(LocalDateTime.now());
		return userRepository.save(newUser);
	}

	public void update(String userId, UserRequestDTO userRequest) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		user.setId(userId);
		user.setName(userRequest.name());
		user.setProfile(userRequest.profile());
		user.setAvatar(userRequest.avatar());

		userRepository.save(user);
	}

	public void delete(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		userRepository.delete(user);

	}

}
