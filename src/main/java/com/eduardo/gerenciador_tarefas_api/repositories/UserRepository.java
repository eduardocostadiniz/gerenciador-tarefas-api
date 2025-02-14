package com.eduardo.gerenciador_tarefas_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.gerenciador_tarefas_api.models.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	User findByEmail(String email);

}
