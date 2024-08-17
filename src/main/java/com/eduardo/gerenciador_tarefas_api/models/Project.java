package com.eduardo.gerenciador_tarefas_api.models;

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
@Table(name = "projects")
public class Project {
	
	public Project(Long projectId) {
		this.id = projectId;
	}

	public Project(ProjectRequestDTO projectDto) {
		this.name = projectDto.name();
		this.description = projectDto.description();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
