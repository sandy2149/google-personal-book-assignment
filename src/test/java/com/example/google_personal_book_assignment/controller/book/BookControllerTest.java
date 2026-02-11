package com.example.personal_book.controller;

import com.example.personal_book.entity.Book;
import com.example.personal_book.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookCotroller.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void addBookFromGoogleSuccessfully() throws Exception {
        Book book = getBook();
        Mockito.when(bookService.addBookFromGoogle("J1001")).thenReturn(book);
        mockMvc.perform(post("/books/J1001")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Spring Boot Book"));
        Mockito.verify(bookService, times(1)).addBookFromGoogle("J1001");
    }

    @Test
    void returnsAllBooks() throws Exception {
        Book book1 = getBook();
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book1));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot Book"));
    }

    public static Book getBook() {
        Book book1 = Book.builder()
                .id(1L)
                .googleId("J1001")
                .title("Spring Boot Book")
                .author("Kathy Siera")
                .pageCount(1010)
                .build();
        return book1;
    }


    //Negative test cases

    @Test
    void returnsBadRequestIfBookAlreadyExists() throws Exception {
        Mockito.when(bookService.addBookFromGoogle("J1001"))
                .thenThrow(new IllegalArgumentException("Book already exists"));

        mockMvc.perform(post("/books/J1001")).andExpect(status().isBadRequest());
    }

    @Test
    void returnsBadRequestIfBookIdNotFound() throws Exception {
        Mockito.when(bookService.addBookFromGoogle("id"))
                .thenThrow(new IllegalArgumentException("Book id not found"));

        mockMvc.perform(post("/books/id")).andExpect(status().isBadRequest());
    }

}