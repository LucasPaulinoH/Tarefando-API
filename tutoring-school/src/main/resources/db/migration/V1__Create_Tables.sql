CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    profile_image VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    role SMALLINT NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE schools (
    id UUID PRIMARY KEY,
    tutor_id UUID NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_image VARCHAR(255),
    cep VARCHAR(255),
    address VARCHAR(255) NOT NULL,
    address_number VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL
);

CREATE TABLE students (
    id UUID PRIMARY KEY,
    guardian_id UUID NOT NULL,
    school_id UUID,
    name VARCHAR(255) NOT NULL,
    birthdate TIMESTAMP(6) WITHOUT TIME ZONE,
    grade VARCHAR(20) NOT NULL
);

CREATE TABLE subjects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    subject_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    images VARCHAR(255)[],
    deadline_date TIMESTAMP(6) WITHOUT TIME ZONE,
    is_concluded BOOLEAN NOT NULL
);
