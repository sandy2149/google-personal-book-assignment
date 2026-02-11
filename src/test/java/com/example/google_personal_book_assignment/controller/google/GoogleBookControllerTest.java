package com.example.google_personal_book_assignment.controller.google;

import static org.junit.jupiter.api.Assertions.*;

import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import com.example.google_personal_book_assignment.service.google.GoogleBookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GoogleBookController.class)
class GoogleBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoogleBookService googleBookService;

    // ✅ Positive Test
    @Test
    void shouldReturnBookFromGoogle() throws Exception {

        GoogleBookResponse response = new GoogleBookResponse();
        response.setId("J1001");

        GoogleBookResponse.VolumeInfo volumeInfo =
                new GoogleBookResponse.VolumeInfo();
        volumeInfo.setTitle("Spring Boot Book");
        volumeInfo.setAuthors(List.of("Kathy Sierra"));
        volumeInfo.setPageCount(500);

        response.setVolumeInfo(volumeInfo);

        Mockito.when(googleBookService.getBookById("J1001"))
                .thenReturn(response);

        mockMvc.perform(get("/google/J1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("J1001"))
                .andExpect(jsonPath("$.volumeInfo.title").value("Spring Boot Book"));
    }

    // Negative Test
    @Test
    void shouldReturnBadRequestIfServiceThrowsException() throws Exception {

        Mockito.when(googleBookService.getBookById("INVALID"))
                .thenThrow(new IllegalArgumentException("Book not found"));

        mockMvc.perform(get("/google/INVALID"))
                .andExpect(status().isBadRequest());
    }
}