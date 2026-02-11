package com.example.google_personal_book_assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleBookResponse {

    private String id;
    private VolumeInfo volumeInfo;

    @Getter
    @Setter
    public static class VolumeInfo {

        private String title;
        private List<String> authors;
        private Integer pageCount;
    }
}