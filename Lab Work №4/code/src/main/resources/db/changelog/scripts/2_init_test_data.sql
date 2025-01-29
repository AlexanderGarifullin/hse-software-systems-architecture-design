--liquibase formatted sql

--changeset alexandergarifullin:8
INSERT INTO test_generation.roles (role) VALUES ('user');

--changeset alexandergarifullin:9
INSERT INTO test_generation.users (login, hashed_password, role_id)
VALUES ('test_user', 'hashed_password_placeholder', (SELECT id FROM test_generation.roles WHERE role = 'user'));
