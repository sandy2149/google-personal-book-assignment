package com.example.google_personal_book_assignment.service.book;

import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import com.example.google_personal_book_assignment.entity.Book;
import com.example.google_personal_book_assignment.repository.BookRepository;
import com.example.google_personal_book_assignment.service.google.GoogleBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GoogleBookService googleBookService;

    @InjectMocks
    private BookService bookService;

    private GoogleBookResponse googleBookResponse;

    @BeforeEach
    void setup() {
        googleBookResponse = new GoogleBookResponse();
        googleBookResponse.setId("J1001");

        GoogleBookResponse.VolumeInfo volumeInfo = new GoogleBookResponse.VolumeInfo();
        volumeInfo.setTitle("Spring Boot Book");
        volumeInfo.setAuthors(List.of("Kathy Siera"));
        volumeInfo.setPageCount(1010);

        googleBookResponse.setVolumeInfo(volumeInfo);
    }

    @Test
    void addBookSuccessfully() {
        Mockito.when(bookRepository.existsByGoogleId("J1001")).thenReturn(false);
        Mockito.when(googleBookService.getBookById("J1001")).thenReturn(googleBookResponse);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Book book1 = bookService.addBookFromGoogle("J1001");

        Assertions.assertEquals("J1001", book1.getGoogleId());
        Assertions.assertEquals("Spring Boot Book", book1.getTitle());
        Assertions.assertEquals("Kathy Siera", book1.getAuthor());
        Assertions.assertEquals(1010, book1.getPageCount());
    }

    @Test
    void returnsAuthorNotFoundIfAuthorsListEmpty() {
        googleBookResponse.getVolumeInfo().setAuthors(List.of());
        Mockito.when(bookRepository.existsByGoogleId("J1001")).thenReturn(false);
        Mockito.when(googleBookService.getBookById("J1001")).thenReturn(googleBookResponse);
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Book result = bookService.addBookFromGoogle("J1001");
        Assertions.assertEquals("Author not found", result.getAuthor());
    }

    @Test
    void returnsBookList() {
        Book book1 = Book.builder().title("Java").build();
        Book book2 = Book.builder().title("React").build();
        Book book3 = Book.builder().title("SQL").build();
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1, book2, book3));
        List<Book> books = bookService.getAllBooks();
        Assertions.assertEquals(3, books.size());
    }
}