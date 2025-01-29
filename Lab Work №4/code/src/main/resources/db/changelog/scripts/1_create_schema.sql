--liquibase formatted sql

--changeset alexandergarifullin:1
CREATE SCHEMA IF NOT EXISTS test_generation;

--changeset alexandergarifullin:2
CREATE TABLE test_generation.roles (
    id      BIGSERIAL   PRIMARY KEY,
    role    TEXT        NOT NULL UNIQUE
);

--changeset alexandergarifullin:3
CREATE TABLE test_generation.users (
    id              BIGSERIAL        PRIMARY KEY,
    login           TEXT             NOT NULL UNIQUE,
    hashed_password TEXT             NOT NULL,
    role_id         BIGINT           NOT NULL,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES test_generation.roles (id) ON DELETE CASCADE
);

--changeset alexandergarifullin:4
CREATE TABLE test_generation.tasks (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    name          TEXT NOT NULL,
    statement     TEXT,
    time_limit    INTEGER,
    memory_limit  INTEGER,
    solve         TEXT,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES test_generation.users (id) ON DELETE CASCADE
);

--changeset alexandergarifullin:5
CREATE TABLE test_generation.tests (
    id    BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL,
    input TEXT NOT NULL,
    CONSTRAINT fk_tests_task FOREIGN KEY (task_id) REFERENCES test_generation.tasks (id) ON DELETE CASCADE
);

--changeset alexandergarifullin:6
CREATE TABLE test_generation.tags (
    id      BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL,
    name    TEXT NOT NULL,
    CONSTRAINT fk_tags_task FOREIGN KEY (task_id) REFERENCES test_generation.tasks (id) ON DELETE CASCADE
);

--changeset alexandergarifullin:7
CREATE TABLE test_generation.parameters (
    id          BIGSERIAL PRIMARY KEY,
    task_id     BIGINT NOT NULL,
    name        TEXT NOT NULL,
    type        TEXT NOT NULL,
    min_value   INTEGER,
    max_value   INTEGER,
    length      INTEGER,
    description TEXT,
    is_input    BOOLEAN NOT NULL,
    order_index INTEGER NOT NULL,
    new_line    BOOLEAN NOT NULL,
    CONSTRAINT fk_parameters_task FOREIGN KEY (task_id) REFERENCES test_generation.tasks (id) ON DELETE CASCADE
);
