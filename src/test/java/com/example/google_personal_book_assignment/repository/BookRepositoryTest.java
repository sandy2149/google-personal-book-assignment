package com.example.google_personal_book_assignment.repository;

import com.example.google_personal_book_assignment.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void returnsTrueIfGoogleIdExists() {

        Book book = Book.builder()
                .googleId("J1001")
                .title("Spring Boot")
                .author("Kathy Siera")
                .pageCount(1010)
                .build();

        bookRepository.save(book);
        boolean exists = bookRepository.existsByGoogleId("J1001");
        Assertions.assertTrue(exists);
    }
}