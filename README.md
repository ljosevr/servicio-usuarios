# REGISTRAR USUARIOS

## Overview
RESTful API Que registra Usuarios.

## Features
- Registrar usuarios
- Consulta Todos los usuarios


## Prerequisites
- Java JDK 17 or higher
- Spring Boot 3.1.6
- H2

## Installation and Running the Application

### Building the Application
`bash mvn clean install`
- La aplicacion Crea la BD al levantar, si necesita crear la BD antes de ejecutar por Primera Vez, use el script create.sql
- Al levantar la primera vez, se crea una semilla (seed) de 3 usuarios de ejemplo
- 
### Running the Application

`java -jar target/myapplication-0.0.1-SNAPSHOT.jar`
## Usando Maven
`mvn spring-boot:run`

### API Documentation
`http://localhost:8080/api-docs`
`http://localhost:8080/swagger-ui/index.html`


## Using the API
### Get All Users
Endpoint: GET /api/users/
Description: Retrieves a list of all users.
Sample cURL Command:
`curl --location 'localhost:8080/api/users/'`

### Create User
Endpoint: POST /api/users/create
Description: Creates a new user. Requiere Token.
Nota:  para efectos de la prueba el Token se genera al levantar la aplicacion
Sample cURL Command:
`curl --location 'localhost:8080/api/users/create' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNdXJ1bmEiLCJleHAiOjE3MDE2NDI3NTV9.La55d-m2SZhCirW__h80HPWDhijUy_CRougZy4e0J0U' \
--header 'Cookie: JSESSIONID=67605B0113131736F8DAB9EB604FC5CA' \
--data-raw '{
"name": "Juan Rodriguez",
"email": "juan@rodriguez.org",
"password": "hunter2",
"phones": [
{
"number": "1234567",
"citycode": "1",
"contrycode": "57"
}
]
}'`

### Update User
Endpoint: PUT /api/users/update/{id}
Description: Update an user. Requiere Token.
Nota:  para efectos de la prueba el Token se genera al levantar la aplicacion
Sample cURL Command:
`curl --location --request PUT 'localhost:8080/api/users/update/be82fc01-29ce-48b8-81c2-28c62e2a87df' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNdXJ1bmEiLCJleHAiOjE3MDE3MjM4MTl9.jxQwgQdu-yC7tWk4pmFKPh83cKNqF-LCHn9WyCg92kw' \
--header 'Cookie: JSESSIONID=67605B0113131736F8DAB9EB604FC5CA' \
--data-raw '{
"id": "be82fc01-29ce-48b8-81c2-28c62e2a87df",
"name": "ljosevr",
"email": "email1@ejemplo.com"
}'`

### Delete User
Endpoint: DELETE /api/users/{id}
Description: Delete an User. Requiere Token.
Nota:  para efectos de la prueba el Token se genera al levantar la aplicacion
Sample cURL Command:
`curl --location --request DELETE 'localhost:8080/api/users/58fe51da-d2f9-4004-8753-f9f6aab416f8' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNdXJ1bmEiLCJleHAiOjE3MDE3MjQwNTd9.EDP1LPC7b69FUAGnYVy0nuhG0tYApaUlIlrqq8aR6nQ' \
--header 'Cookie: JSESSIONID=67605B0113131736F8DAB9EB604FC5CA'`

### Diagrama de Clases
![Diagrama de Clases.png](Diagrama%20de%20Clases.png)