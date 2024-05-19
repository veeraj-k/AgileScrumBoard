# Agile Scrum Board 
## Description
A full-stack web application using Spring Boot, React.js, PostgreSQL, and react-beautiful-dnd library,
enabling agile project management with features like task viewing, drag-and-drop functionality, sprint management,
backlog, task comments.

## Features
- RESTful APIs: Backend APIs built with Spring Boot for handling CRUD operations and business logic.
- Task Management : Create and Update tasks.
- Drag and Drop: Drag-and-drop functionality for moving tasks between project stages or sprints.
- Sprint Management: Organize tasks into sprints for project planning and tracking.
- Backlog: Maintain a backlog of tasks for future sprints.
- Task Comments: Add comments to tasks for collaboration and discussion.
  
## Technologies
- Spring boot - JPA,Hibernate
- Java
- PostgreSQL
  
## Installation
### Using Maven

1. Clone the repository: `git clone https://github.com/veeraj-k/AgileScrumBoard.git`
2. Navigate to the project directory: `cd backend`
3. Add the database url ,user and password in the .env file
   ```bash
      SPRING_DATABASE_URL=localhost:3306
      SPRING_DATABASE_USER=admin
      SPRING_DATABASE_PASSWORD=admin
   ```
4. Build the project: `./mvnw clean install`

### Using Docker

1. Clone the repository: `git clone https://github.com/veeraj-k/AgileScrumBoard.git`
2. Navigate to the project directory: `cd backend`
3. 3. Add the database url ,user and password in the .env file
   ```bash
      SPRING_DATABASE_URL=dockerinternal.localhost:3306
      SPRING_DATABASE_USER=admin
      SPRING_DATABASE_PASSWORD=admin
   ```
4. Build the Docker image: `docker build -t agile-scrum-board-backend .`

## Usage

### Using Maven

1. Run the application: `./mvnw spring-boot:run`
2. The backend APIs will be accessible at `http://localhost:8080`

### Using Docker

1. Run the Docker container: `docker run -p --env-file ./your-env-file 8080:8080 agile-scrum-board-backend`
2. The backend APIs will be accessible at `http://localhost:8080`
