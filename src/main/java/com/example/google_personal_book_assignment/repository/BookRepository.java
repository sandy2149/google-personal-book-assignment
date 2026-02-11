package com.example.google_personal_book_assignment.repository;

import com.example.google_personal_book_assignment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    boolean existsByGoogleId(String googleId);
}
