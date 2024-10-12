package com.eduardo.gerenciador_tarefas_api.services;

import com.eduardo.gerenciador_tarefas_api.exceptions.UserAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.exceptions.UserNotFoundException;
import com.eduardo.gerenciador_tarefas_api.models.User;
import com.eduardo.gerenciador_tarefas_api.models.UserRequestDTO;
import com.eduardo.gerenciador_tarefas_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Page<User> getAll(Pageable page) {
		return userRepository.findAll(page);
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
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		user.setId(userId);
		user.setName(userRequest.name());
		user.setProfile(userRequest.profile());
		user.setAvatar(userRequest.avatar());

		userRepository.save(user);
	}

	public void delete(String userId) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

		userRepository.delete(user);
	}

}
