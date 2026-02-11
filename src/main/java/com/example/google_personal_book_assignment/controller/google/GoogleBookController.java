package com.example.google_personal_book_assignment.controller.google;

import com.example.google_personal_book_assignment.dto.GoogleBookResponse;
import com.example.google_personal_book_assignment.service.google.GoogleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/google")
@RequiredArgsConstructor
public class GoogleBookController {

    private final GoogleBookService googleBookService;

    @GetMapping("/{id}")
    public GoogleBookResponse getBookFromGoogle(@PathVariable String id) {
        return googleBookService.getBookById(id);
    }
}
