package com.example.google_personal_book_assignment.service.book;


import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import com.example.google_personal_book_assignment.entity.Book;
import com.example.google_personal_book_assignment.repository.BookRepository;
import com.example.google_personal_book_assignment.service.google.GoogleBookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final GoogleBookService googleBookService;

    public Book addBookFromGoogle(String googleId) {
        log.info("addBookFromGoogle");

        //Checks if duplicate
        if (bookRepository.existsByGoogleId(googleId)) {
            throw new IllegalArgumentException("Book already exists");
        }

        GoogleBookResponse response = googleBookService.getBookById(googleId);

        //Cheks if googleid response not null or blank
        if (Objects.isNull(response) || Objects.isNull(response.getId())) {
            throw new IllegalArgumentException("Book id not found");
        }

        GoogleBookResponse.VolumeInfo volumeInfo = response.getVolumeInfo();

        String author = (Objects.nonNull(volumeInfo.getAuthors()) && !volumeInfo.getAuthors().isEmpty())
                ? volumeInfo.getAuthors().get(0)
                : "Author not found";

        Book book = Book.builder()
                .googleId(response.getId())
                .title(response.getVolumeInfo().getTitle())
                .author(author)
                .pageCount(response.getVolumeInfo().getPageCount())
                .build();

        Book savedBook = bookRepository.save(book);

        log.info("Book saved successfully with id {}", savedBook.getId());
        return savedBook;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
