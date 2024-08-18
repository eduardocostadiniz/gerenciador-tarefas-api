package com.eduardo.gerenciador_tarefas_api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "tasks")
public class Task {

	public Task(TaskRequestDTO task) {
		this.name = task.name();
		this.description = task.description();
		this.status = task.status();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private String status;

	@OneToOne
	@JoinColumn(name = "owner_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + ", user="
				+ user + ", project=" + project + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
