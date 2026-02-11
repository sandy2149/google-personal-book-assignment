# Google Personal Book Assignment

## About Application
This is a Spring Boot application 
which get list of books from Google Books and save it in
a personal book list. Also fetch all books from personal book DB.

The application:
- Get book details from Google Books
- Mapping of selected fields with local entity
- Save books into H2 database
- Cheks duplicate entries
- Post and Get Rest endpoints developed


## Tech used
- Java 17
- Spring Boot 3.5.10
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- JUnit 5 & Mockito


## Assignment Steps
- Constructor-based dependency injection is used 
- Followed layered architecture
- Transaction management is used in service layer
- Logs are implemented
- Positive & negative test cases included
- Committed all files with history
- 
 ## Followed layered architecture:
- Business logic in service layer only
- Transaction management at service layer
- Database interaction at Repository layer 

## How to run
-clone the repository using 
  -- git clone https://github.com/sandy2149/google-personal-book-assignment.git

-Build the project
  -- mvn clean install

-Run the application
  --Run GooglePersonalBookAssignmentApplication.java

-Apllication starts
  --http://localhost:8080

-Add book from Google
  --POST http://localhost:8080/books/{googleId}

-Get All Books
  --GET http://localhost:8080/books

-H2 database
  --http://localhost:8080/h2-console

