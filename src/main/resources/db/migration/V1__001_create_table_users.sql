CREATE TABLE IF NOT EXISTS "users" (
    email VARCHAR(75) NOT NULL,
    name VARCHAR(75) NOT NULL,
    profile VARCHAR(50) NOT NULL,
    avatar VARCHAR(1000),
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (email)
);