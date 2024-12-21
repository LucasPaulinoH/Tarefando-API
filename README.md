Tarefando API 
=============

A REST API for integrated academic task management and tracking.

---
## Class diagram ✒️

The following diagram was used as representation of the structure and relationships between key entities, serving as the foundation for the API design.

![img](class-diagram.png)
---

## Features

| Feature                         | Description                                                                                              |
| ------------------------------- | -------------------------------------------------------------------------------------------------------- |
| **Login**                       | Users can authenticate through a secure (JWT) login feature.                                             |
| **Tutor registration**          | Users can register as a **TUTOR**.                                                                       |
| **Guardian registration**       | Users can register as a **GUARDIAN**.                                                                    |
| **Change password**             | Users can update their passwords.                                                                        |
| **CRUD for classes**            | **TUTORS** can create, view, edit, and delete classes.                                                   |
| **CRUD for students**           | **GUARDIANS** can create, view, edit, and delete students.                                               |
| **Student linking via QR Code** | **GUARDIANS** can link their students to a class via QR code.                                            |
| **CRUD for tasks**              | Any associated user (**TUTOR** or **GUARDIAN**) can create, view, edit, and delete tasks from a student. |
| **Conclude task**               | Any associated user can mark a task as concluded.                                                        |
| **Filter tasks by status**      | Any associated user can filter student tasks by status: PENDENT, DELAYED, and CONCLUDED.                 |
| **CRUD for announcements**      | **TUTORS** can create, view, edit, and delete announcements.                                             |
| **Send announcements**          | **TUTORS** can send created announcements to **GUARDIANS** of students linked to one of their classes.   |

---

## Documentation 📖
Swagger UI: https://tarefando-api.onrender.com/swagger-ui/index.html

## License 📃

This project is licensed under the terms of the **MIT** license.
