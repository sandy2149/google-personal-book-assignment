package com.example.google_personal_book_assignment.service.google;

import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class GoogleBookServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleBookService googleBookService;

    private final String url = "https://www.googleapis.com/books/v1";
    private final String completeUrl = url + "/volumes/J1001";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(googleBookService,"baseUrl", url);
    }

    @Test
    void getBookSuccessfully() {
        GoogleBookResponse googleBookResponse = new GoogleBookResponse();
        googleBookResponse.setId("J1001");

        Mockito.when(restTemplate.getForObject(completeUrl, GoogleBookResponse.class)).thenReturn(googleBookResponse);
        GoogleBookResponse googleBookResponseResult = googleBookService.getBookById("J1001");

        Assertions.assertNotNull(googleBookResponseResult);
        Assertions.assertEquals("J1001", googleBookResponseResult.getId());
    }

    // Negative Cases

    @Test
    void gettingNullWhenApiReturningNull() {
        Mockito.when(restTemplate.getForObject(completeUrl, GoogleBookResponse.class)).thenReturn(null);
        GoogleBookResponse googleBookResponse = googleBookService.getBookById("J1001");
        Assertions.assertNull(googleBookResponse);
    }

    @Test
    void throwsExceptionIfApiFails() {
        Mockito.when(restTemplate.getForObject(completeUrl, GoogleBookResponse.class)).thenThrow(new RuntimeException("API fails"));
        Assertions.assertThrows(RuntimeException.class, () -> googleBookService.getBookById("J1001")
        );
    }

}