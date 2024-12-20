# Tennis club reservation app

## Description

This project is a server app for making reservations for a tennis club.

## Features

- Implementation of modern software development practices.
- Integration with Spring Data JPA for ORM and database interaction.
- Usage of Jakarta EE standards for full-stack application support.
- Simplified code with Lombok annotations for reducing boilerplate code.
- Modular domain-based contexts for clearer separation of concerns.

## Prerequisites
For building and running the application you need:

- Java 17
- Maven 3

## API endpoints

The project exposes endpoints at **/api/--**. Available endpoints are: 
- courts 
- reservations
- users

Additionally, those are the endpoints for auth at **/api/auth/--**.:
- login
- logout
- refresh-token

## Security
All endpoints are protected with the Bearer authentication scheme (except for **/api/auth/login**).
Every request has to include the **Authorization header** like that:
```
Authorization: Bearer <token>
```

## Dependencies

The project uses the following dependencies:

1. **Spring Framework**:
    - `spring-boot-starter-data-jpa`: For ORM and database integration.
    - `spring-boot-starter-web`: For building RESTful APIs and web applications.
    - `spring-boot-starter-validation`: For validation purposes.
    - `spring-boot-starter-security`: For implementing security features.
2. **Lombok**: Simplifies code by auto-generating boilerplate.
3. **H2 Database**
4. **MapStruct**:
    - Used for mapping between DTOs and entities.
5. **JWT**:
    - `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: For JWT-based authentication.
6. **Testing**:
    - `spring-boot-starter-test`: For writing unit tests.
    - `spring-security-test`: For testing security functionality.

Additionally, this project uses Maven plugins for building and compiling the project, such as:

- `spring-boot-maven-plugin`: For running the Spring Boot application.
- `maven-compiler-plugin`: To specify Java source and target compatibility.

## Configuration

You can configure the project in the `application.properties`. 

- spring.datasource.username: DB username
- spring.datasource.password: DB password

- seed-data: if set to true, DB will be populated with initial data
- jwt.secret: secret key used to sign and verify JWT
- jwt.expiration: validity period of access tokens
- jwt.refreshExpiration: validity period of refresh tokens

## Running the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/0-mar/TennisClubReservationApp
   ```
2. Navigate to the project directory:
   ```bash
   cd TennisClubReservationApp
   ```
3. Configure the project (seeding, db connection details) in the `application.properties`.
4. Build the project using Maven:
   ```bash
   mvn clean install
   ```
5. Run the project using Maven:
   ```bash
   mvn spring-boot:run
   ```