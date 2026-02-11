package com.example.google_personal_book_assignment.controller.book;

import com.example.google_personal_book_assignment.entity.Book;
import com.example.google_personal_book_assignment.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
    void returnsAllBooks() throws Exception {

        Book book1 = getBook();
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book1));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot Book"));
    }

    @Test
    void addBookFromGoogleSuccessfully() throws Exception {

        Book book = getBook();
        Mockito.when(bookService.addBookFromGoogle("J1001")).thenReturn(book);

        mockMvc.perform(post("/books/J1001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.googleId").value("J1001"))
                .andExpect(jsonPath("$.title").value("Spring Boot Book"))
                .andExpect(jsonPath("$.author").value("Kathy Siera"));
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

    //  Negative Case (ID Already exists)
    @Test
    void returnsBadRequestIfBookIdNotFound() throws Exception {
        Mockito.when(bookService.addBookFromGoogle("id")).thenThrow(new IllegalArgumentException("Book already exists"));
        mockMvc.perform(post("/books/id")).andExpect(status().isBadRequest());
    }
}