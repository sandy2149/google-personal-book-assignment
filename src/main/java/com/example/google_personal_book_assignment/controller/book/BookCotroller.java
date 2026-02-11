package com.example.google_personal_book_assignment.controller.book;


import com.example.google_personal_book_assignment.entity.Book;
import com.example.google_personal_book_assignment.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookCotroller {

    private final BookService bookService;

    @PostMapping("/{googleId}")
    public ResponseEntity<Book> addBook(@PathVariable String googleId) {
        Book book = bookService.addBookFromGoogle(googleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
