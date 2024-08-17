CREATE TABLE IF NOT EXISTS "tasks" (
	id BIGSERIAL,
    name VARCHAR(1000) NOT NULL,
    description VARCHAR(5000) NOT null,
    status VARCHAR(100) NOT null,
    owner_id VARCHAR(100),
    project_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    PRIMARY key (id),
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES projects (id)    
);