INSERT INTO users (id, name, profile_image, email, phone, role, password) 
VALUES 
('6822e532-e98c-4dc9-a341-bdc031da0ce6', 'Lucas Paulino', NULL, 'lucas@email.com', '(84) 99199-9999', 1, '$2a$10$zvfngUvI7TVP8PiBhYnaEe.NZ.jWxlqJvHbxsRYoxbKvUQ4VLKOGS'),
('d6b0e8f0-0d15-40bf-8003-2c528482e4d0', 'Lady Paulino', NULL, 'lady@email.com', '(84) 99199-9999', 0, '$2a$10$zvfngUvI7TVP8PiBhYnaEe.NZ.jWxlqJvHbxsRYoxbKvUQ4VLKOGS');


INSERT INTO schools (id, tutor_id, name, description, phone, email, profile_image, cep, address, address_number, district, city, state) 
VALUES 
('d6b0e8f0-0d15-40bf-8003-2c528482e4d0', 'd6b0e8f0-0d15-40bf-8003-2c528482e4d0', 'Tia Lady Ajuda', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.', '(84) 99888-9999', 'tialady@email.com', NULL, NULL, 'Rua Nísia Floresta', '100', 'Boa Vista', 'Mossoró', 'RN');


INSERT INTO students (id, user_id, school_id, name, birthdate, grade)
VALUES
('1e36205c-00bc-4c80-89e0-8aac732c81c2', '6822e532-e98c-4dc9-a341-bdc031da0ce6', NULL, 'Luiggi Paulino', '2024-07-11', '7a série');

INSERT INTO subjects (id, name)
VALUES
('112ecd01-1104-40b9-be41-b424027c768c', 'Matemática'),
('e00a7f1f-347c-415b-801f-63934b0e177d', 'Ciências'),
('6679c602-caf9-4d48-a9ef-13a4218c1c7a', 'Português'),
('6b97cbcf-ec62-48a5-8351-c98815fdf7f0' , 'História'),
('390e4133-6a33-4bf1-b994-4aed4d1921d8', 'Geografia');

INSERT INTO tasks (id, subject_id, title, description, images, deadline_date, is_concluded)
VALUES
('60305b5c-3605-44b5-b25b-36f22317c5ee', '112ecd01-1104-40b9-be41-b424027c768c', 'Exercício de Bhaskara', '', NULL, '2024-09-09', FALSE);

