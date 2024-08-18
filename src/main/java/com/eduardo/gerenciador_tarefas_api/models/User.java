package com.eduardo.gerenciador_tarefas_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	public User(String userId) {
		this.id = userId;
	}

	public User(UserRequestDTO user) {
		this.email = user.email();
		this.name = user.name();
		this.profile = user.profile();
		this.avatar = user.avatar();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String email;
	private String name;
	private String profile;
	private String avatar;
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", profile=" + profile + ", avatar=" + avatar
				+ ", createdAt=" + createdAt + "]";
	}

}
