package com.example.google_personal_book_assignment.service.google;

import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleBookService {

    private final RestTemplate restTemplate;

    @Value("${google.books.base-url}")
    private String baseUrl;

    public GoogleBookResponse fetchBookById(String googleId) {
        String url = baseUrl + "/volumes/" + googleId;
        return restTemplate.getForObject(url, GoogleBookResponse.class);
    }
}
